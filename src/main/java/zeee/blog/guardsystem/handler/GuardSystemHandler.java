package zeee.blog.guardsystem.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.Category;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.common.operlog.service.OperlogService;
import zeee.blog.common.redis.RedisClient;
import zeee.blog.guardsystem.dao.GuestDao;
import zeee.blog.guardsystem.dao.GuestVisitInfoDao;
import zeee.blog.guardsystem.entity.*;
import zeee.blog.utils.Sm4Util;
import zeee.blog.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private RedisClient redisClient;

    /**
     * 访客访问信息处理，主要包含以下步骤
     * 1.访问信息处理
     * 2.访客信息处理
     * 3.回显信息处理
     *
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
            guestVisitInfoDO.setValidation(true);
            //先把时间转换为Date，然后再转换为对应天数
            Date startDate = DateUtil.parse(requestInfo.getStartTime());
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
            guestResponseVO.setStartTime(guestVisitInfoDO.getStartTime());
            guestResponseVO.setEndTime(guestVisitInfoDO.getEndTime());
            return guestResponseVO;
        } catch (Exception e) {
            log.error("add guest visit info error", e);
            operlogService.addLog(null, null, new Date(), null, Category.GUARD,
                    "添加访客访问信息失败", RESULT_FAILURE, e.getMessage());
            throw new AppException(ErrorCodes.ADD_GUEST_VISIT_INFO_ERRRO);
        }
    }

    /**
     * 通过手机号和校验码查询访问信息，用于界面查询信息
     *
     * @param phoneNumber 手机号
     * @param checkCode   校验码
     * @return 访问信息，单个
     */
    public GuestResponseVO queryGuestVisitInfo(String phoneNumber, String checkCode) {
        // 根据手机号和校验码查询visit info，先尝试在redis里面查询
        String key = StrUtil.format("{}_{}_{}", GuardConstants.VISIT_PREFIX, phoneNumber, checkCode);
        GuestResponseVO guestVoFormRedis;
        Object o = redisClient.get(key);
        if (Objects.nonNull(o)) {
            guestVoFormRedis = (GuestResponseVO) o;
            log.info("query visit info from redis successfully");
            return guestVoFormRedis;
        }

        // 如果redis里面没有查到，从数据库查，并写入redis
        List<GuestVisitInfoDO> guestVisitInfos =
                guestVisitInfoDao.queryGuestVisitInfoByPhoneNumberAndCheckCode(phoneNumber, checkCode);
        if (CollectionUtils.isEmpty(guestVisitInfos)) {
            return null;
        }
        // 可能会有多个的情况，返回最新的
        guestVisitInfos.sort((o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime()));
        GuestVisitInfoDO info = guestVisitInfos.get(0);
        if (Objects.nonNull(info)) {
            log.info("query visit info from database successfully");
            // 存入redis，key为guest_xxxxx_xxx
            redisClient.set(key, info, GuardConstants.ONE_DAY_TIME);
            // 返回VO
            GuestResponseVO guestVo = new GuestResponseVO();
            guestVo.setUserName(info.getGuestName());
            guestVo.setPhoneNumber(info.getPhoneNumber());
            guestVo.setCheckCode(info.getCheckCode());
            guestVo.setUuid(info.getUuid());
            guestVo.setStartTime(info.getStartTime());
            guestVo.setEndTime(info.getEndTime());
            return guestVo;
        } else {
            return null;
        }

    }


    /**
     * 检查是否准许进入，如果准许进入，validation置为false，表示visit信息已失效
     *
     * @param uuid uuid
     * @return 是否准许
     */
    public boolean checkPermit(String uuid) {
        // 根据uuid查询visit信息，先从redis里面拿，拿不到再从数据库拿
        GuestVisitInfoDO info = queryVisitInfoByUuid(uuid);
        // 拿到信息后，对比时间，只有在准许时间内才能准入
        Date now = new Date();
        if (now.before(info.getStartTime()) || now.after(info.getEndTime())) {
            throw new AppException(ErrorCodes.NOT_IN_PERMIT_TIME);
        }
        // 查看validation信息，是否已作废
        if (!info.getValidation()) {
            throw new AppException(ErrorCodes.VALIDATION_FALSE);
        }
        // 准入后，validation置为false，更新数据库，删除redis中的数据
        info.setValidation(false);
        guestVisitInfoDao.update(info);
        redisClient.del(uuid);
        return true;
    }

    /* ==========================private method begin=====================*/

    /**
     * 判断访问开始时间是否在规定范围内
     *
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
     *
     * @param requestInfo 访问信息
     */
    private void guestInfoCheck(GuestRequestInfo requestInfo) {
        String key = StrUtil.format("{}_{}", requestInfo.getGuestName());
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
                            // 数据有更新，删除缓存
                            redisClient.del(key);
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
     *
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
     * 通过uuid查询visit信息，uuid一般都是唯一，不考虑多个查询结果的情况
     *
     * @param uuid uuid
     * @return visit info
     */
    private GuestVisitInfoDO queryVisitInfoByUuid(String uuid) {
        // 首先尝试从redis中查询，查询不到，再去数据库中查询
        GuestVisitInfoDO guestVisitInfoDO = (GuestVisitInfoDO) redisClient.get(uuid);
        if (Objects.nonNull(guestVisitInfoDO)) {
            return guestVisitInfoDO;
        } else {
            guestVisitInfoDO = guestVisitInfoDao.queryGuestVisitInfoByUuid(uuid);
            redisClient.set(uuid, guestVisitInfoDO);
            return guestVisitInfoDO;
        }
    }
}
