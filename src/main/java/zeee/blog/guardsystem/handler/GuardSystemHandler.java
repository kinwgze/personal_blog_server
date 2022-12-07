package zeee.blog.guardsystem.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zeee.blog.common.Category;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.common.operlog.service.OperlogService;
import zeee.blog.guardsystem.dao.GuestDao;
import zeee.blog.guardsystem.dao.GuestVisitInfoDao;
import zeee.blog.guardsystem.entity.*;
import zeee.blog.utils.Sm4Util;
import zeee.blog.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.*;

import static zeee.blog.common.operlog.entity.OperationLog.RESULT_FAILURE;
import static zeee.blog.common.operlog.entity.OperationLog.RESULT_SUCCESS;

/**
 * @author wz
 * @date 2022/11/21
 */
@Service("guardSystemHandler")
public class GuardSystemHandler {

    private static final Logger log = LoggerFactory.getLogger(GuardSystemHandler.class);

    @Resource
    private GuestVisitInfoDao guestVisitInfoDao;

    @Resource
    private GuestDao guestDao;

    @Resource
    private OperlogService operlogService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 访客访问信息处理，主要包含以下步骤
     * 1.访问信息处理
     * 2.访客信息处理
     * 3.回显信息处理
     * @param requestInfo
     * @return
     */
    public GuestResponseVO addGuestVisitInfo(GuestRequestInfo requestInfo) {
        GuestVisitInfoDO guestVisitInfoDO;
        try {
            // 处理访客信息
            guestVisitInfoDO = new GuestVisitInfoDO();
            // 基本信息填充
            guestVisitInfoDO.setGuestName(requestInfo.getGuestName());
            guestVisitInfoDO.setPhoneNumber(requestInfo.getPhoneNumber());
            guestVisitInfoDO.setCommitTime(new Date());
            //先把时间转换为Date，然后再转换为对应天数
            Date startDate = new Date(requestInfo.getStartTimeStamp());
            guestVisitInfoDO.setStartTime(startDate);
            String dayDate = DateUtil.formatDate(startDate);
            // 准许的时间为申请时间当天08：00：00-20：00：00
            Long endTime = checkStartAndEndTime(startDate);
            if (endTime != null) {
                guestVisitInfoDO.setEndTime(new Date(endTime));
            }
            // uuid是唯一的。该方法生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42，此处只取前六位作为校验码
            String checkCode = IdUtil.simpleUUID().substring(0, 6);
            guestVisitInfoDO.setCheckCode(checkCode);
            // 生成 访客名-手机号-校验码 格式的字符串，使用sm4加密后的字符串作为uuid
            String uuidTmp = StrUtil.format("{}-{}-{}", requestInfo.getGuestName(),
                    requestInfo.getPhoneNumber(), checkCode);
            String uuid;
            try {
                uuid = Sm4Util.encryptDataEcb(uuidTmp);
            } catch (Exception e) {
                log.error("encrypt uuid error", e);
                throw new AppException(ErrorCodes.SM4_ENCRYPT_ERROR);
            }
            guestVisitInfoDO.setUuid(uuid);
            guestVisitInfoDO.setNotes(requestInfo.getNote());
            // 插入数据库
            guestVisitInfoDao.insert(guestVisitInfoDO);
            // 插入redis中
            String key = GuardConstants.GUEST_PREFIX + guestVisitInfoDO.getPhoneNumber();
            redisTemplate.opsForValue().set(key, guestVisitInfoDO);
            // 处理访客信息，判断是否是第一次访问
            guestInfoCheck(requestInfo);
            // 返回信息处理
            operlogService.addLog(null, null, new Date(), null, Category.GUARD,
                    "添加访客访问信息成功", RESULT_SUCCESS, null);
            GuestResponseVO guestResponseVO = new GuestResponseVO();
            guestResponseVO.setUserName(guestVisitInfoDO.getGuestName());
            guestResponseVO.setPhoneNumber(guestVisitInfoDO.getPhoneNumber());
            guestResponseVO.setCheckCode(guestVisitInfoDO.getCheckCode());
            guestResponseVO.setUuid(guestVisitInfoDO.getUuid());
            return guestResponseVO;
        } catch (Exception e) {
            log.error("add guest visit info error", e);
            operlogService.addLog(null, null, new Date(), null, Category.GUARD,
                    "添加访客访问信息失败", RESULT_FAILURE, e.getMessage());
            throw new AppException(ErrorCodes.ADD_GUEST_VISIT_INFO_ERRRO);
        }
    }

    /**
     * 判断访问开始时间是否在规定范围内
     * @param startDate 开始时间
     * @return 若在，返回结束时间，若不在，抛出异常
     */
    private Long checkStartAndEndTime(Date startDate) {
        String dayDate = DateUtil.formatDate(startDate);
        Long permitStartTime;
        Long permitEndTime;
        try {
            // 拿到当天的规定时间范围
            permitStartTime = TimeUtil.getTimestamp(dayDate, GuardConstants.START_TIME);
            permitEndTime = TimeUtil.getTimestamp(dayDate, GuardConstants.END_TIME);
        } catch (Exception e) {
            log.error("get time error", e);
            throw new AppException(ErrorCodes.CONVERT_TIME_ERROR);
        }
        // 开始时间小于规定时间
        if (Objects.nonNull(permitStartTime) && startDate.getTime() < permitStartTime) {
            log.error("request time error, start time is: " + startDate);
            throw new AppException(ErrorCodes.REQUEST_TIME_ERROR);
        }
        // 如果规定结束时间小于开始时间
        if (Objects.nonNull(permitEndTime)) {
            if (permitEndTime < startDate.getTime()) {
                log.error("request time error, start time is: " + startDate);
                throw new AppException(ErrorCodes.REQUEST_TIME_ERROR);
            } else {
               return permitEndTime;
            }
        }
        return null;
    }

    /**
     * 处理访客信息，判断是否是第一次访问
     * @param requestInfo 访问信息
     */
    private void guestInfoCheck(GuestRequestInfo requestInfo) {
        try {
            // 判断访客是否是第一次
            if (StringUtils.isNotEmpty(requestInfo.getPhoneNumber())) {
                List<GuestDO> guestList = queryGuestByPhoneNumber(requestInfo.getPhoneNumber());
                if (CollectionUtils.isNotEmpty(guestList)) {
                    // 如果不是第一次，更新访问次数
                    for (GuestDO guest : guestList) {
                        if (guest.getPhoneNumber().equals(requestInfo.getPhoneNumber())
                                && guest.getName().equals(requestInfo.getGuestName())) {
                            guest.setStatistics(guest.getStatistics() + 1);
                            guestDao.updateById(guest);
                            break;
                        }
                    }
                } else {
                    // 如果是第一次，访客信息保存到数据库
                    GuestDO guest = new GuestDO();
                    guest.setName(requestInfo.getGuestName());
                    guest.setPhoneNumber(requestInfo.getPhoneNumber());
                    guest.setStatistics(1);
                    guestDao.insert(guest);
                }
            }
        } catch (Exception e) {
            throw new AppException(ErrorCodes.GUEST_INFO_ERROR);
        }
    }

    /**
     * 通过手机号，查询访客信息
     * @param phoneNumber 手机号
     * @return 访客信息，List形式
     */
    private List<GuestDO> queryGuestByPhoneNumber(String phoneNumber) {
        List<GuestDO> guestList = guestDao.queryGuestByPhoneNumber(phoneNumber);
        if (CollectionUtils.isNotEmpty(guestList)) {
            return guestList;
        }
        return null;
    }

    /**
     * 通过手机号和校验码查询访客信息
     * @param phoneNumber 手机号
     * @param checkCode 校验码
     * @return 访客信息，单个
     */
    public GuestVisitInfoDO checkGuestVisitInfo(String phoneNumber, String checkCode) {
        // 根据手机号和校验码查询visit info
        List<GuestVisitInfoDO> guestVisitInfos =
                guestVisitInfoDao.queryGuestVisitInfoByPhoneNumberAndCheckCode(phoneNumber, checkCode);
        if (CollectionUtils.isEmpty(guestVisitInfos)) {
            return null;
        }
        // 可能会有多个的情况，返回最新的
        guestVisitInfos.sort((o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime()));
        return guestVisitInfos.get(0);
    }
}
