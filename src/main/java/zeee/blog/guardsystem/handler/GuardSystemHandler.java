package zeee.blog.guardsystem.handler;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.guardsystem.dao.GuestDao;
import zeee.blog.guardsystem.dao.GuestVisitInfoDao;
import zeee.blog.guardsystem.entity.*;
import zeee.blog.utils.Sm4Util;
import zeee.blog.utils.TimeUtil;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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

    public GuestResponseVO addGuestVisitInfo(GuestRequestInfo requestInfo) {

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
                // 如果是第一次，信息保存到数据库
                GuestDO guest = new GuestDO();
                guest.setName(requestInfo.getGuestName());
                guest.setPhoneNumber(requestInfo.getPhoneNumber());
                guest.setStatistics(1);
                guestDao.insert(guest);
            }
        }

        // 处理访客信息
        GuestVisitInfoDO guestVisitInfoDO = new GuestVisitInfoDO();
        guestVisitInfoDO.setGuestName(requestInfo.getGuestName());
        guestVisitInfoDO.setPhoneNumber(requestInfo.getPhoneNumber());
        guestVisitInfoDO.setCommitTime(System.currentTimeMillis());
        guestVisitInfoDO.setStartTime(requestInfo.getStartTime());
        // 准许的时间为申请时间到当天晚上20：00：00
        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
        long endTime = TimeUtil.getTimestamp(dayFormat.format(requestInfo.getStartTime()), GuardConstants.END_TIME);
        if (endTime < requestInfo.getStartTime()) {
            log.error("request time error: " + requestInfo.getStartTime());
            throw new AppException(ErrorCodes.REQUEST_TIME_ERROR);
        } else {
            guestVisitInfoDO.setEndTime(endTime);
        }
        // uuid是唯一的。该方法生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42，此处只取前六位作为校验码
        String checkCode = IdUtil.simpleUUID().substring(0, 6);
        guestVisitInfoDO.setCheckCode(checkCode);
        // 生成 访客名-手机号-校验码 格式的字符串，使用sm4加密后的字符串作为uuid
        String uuidTmp = StrUtil.format("{}-{}-{}", requestInfo.getGuestName(), requestInfo.getPhoneNumber(), checkCode);
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

        // 返回信息处理
        GuestResponseVO guestResponseVO = new GuestResponseVO();
        guestResponseVO.setUserName(guestVisitInfoDO.getGuestName());
        guestResponseVO.setPhoneNumber(guestVisitInfoDO.getPhoneNumber());
        guestResponseVO.setCheckCode(guestVisitInfoDO.getCheckCode());
        guestResponseVO.setUuid(guestVisitInfoDO.getUuid());
        return guestResponseVO;
    }

    private List<GuestDO> queryGuestByPhoneNumber(String phoneNumber) {
        List<GuestDO> guestList = guestDao.queryGuestByPhoneNumber(phoneNumber);
        if (CollectionUtils.isNotEmpty(guestList)) {
            return guestList;
        }
        return null;
    }
}
