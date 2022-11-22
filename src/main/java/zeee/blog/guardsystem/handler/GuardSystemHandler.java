package zeee.blog.guardsystem.handler;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.guardsystem.dao.GuestInfoDao;
import zeee.blog.guardsystem.entity.GuestInfo;
import zeee.blog.guardsystem.entity.GuestRequestInfo;
import zeee.blog.utils.Sm4Util;
import zeee.blog.utils.TimeUtil;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/11/21
 */
@Service("guardSystemHandler")
public class GuardSystemHandler {

    private static final Logger log = LoggerFactory.getLogger(GuardSystemHandler.class);

    @Resource
    private GuestInfoDao guestInfoDao;

    public void addGuestRequest(GuestRequestInfo requestInfo) {
        GuestInfo guestInfo = new GuestInfo();
        guestInfo.setUserName(requestInfo.getGuestName());
        guestInfo.setPhoneNumber(requestInfo.getPhoneNumber());
        guestInfo.setCommitTime(System.currentTimeMillis());
        guestInfo.setStartTime(requestInfo.getStartTime());
        // 准许的时间为申请时间到当天晚上20：00：00
        long endTime = TimeUtil.getTimestamp("20:00:00");
        if (endTime < requestInfo.getStartTime()) {
            log.error("request time error: " + requestInfo.getStartTime());
            throw new AppException(ErrorCodes.REQUEST_TIME_ERROR);
        } else {
            guestInfo.setEndTime(endTime);
        }
        guestInfo.setUuid(IdUtil.simpleUUID());
        try {
            String checkCode = Sm4Util.encryptDataEcb(requestInfo.getGuestName() + requestInfo.getPhoneNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        guestInfo.setCheckCode();
    }
}
