package zeee.blog.guardsystem.handler;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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

    public String addGuestRequest(GuestRequestInfo requestInfo) {
        GuestInfo guestInfo = new GuestInfo();
        guestInfo.setUserName(requestInfo.getGuestName());
        guestInfo.setPhoneNumber(requestInfo.getPhoneNumber());
        guestInfo.setCommitTime(System.currentTimeMillis());
        guestInfo.setStartTime(requestInfo.getStartTime());
        // 准许的时间为申请时间到当天晚上20：00：00
        // 这里有个bug，目前只能拿到申请当天的，应该是允许页面选择日期
        long endTime = TimeUtil.getTimestamp("20:00:00");
        if (endTime < requestInfo.getStartTime()) {
            log.error("request time error: " + requestInfo.getStartTime());
            throw new AppException(ErrorCodes.REQUEST_TIME_ERROR);
        } else {
            guestInfo.setEndTime(endTime);
        }
        // uuid是唯一的。该方法生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42，此处只取前六位作为校验码
        String checkCode = IdUtil.simpleUUID().substring(0 ,6);
        guestInfo.setCheckCode(checkCode);
        // 生成 访客名-手机号-校验码 格式的字符串，使用sm4加密后的字符串作为uuid
        String uuidTmp = StrUtil.format("{}-{}-{}", requestInfo.getGuestName(), requestInfo.getPhoneNumber(), checkCode);
        String uuid = null;
        try {
            uuid = Sm4Util.encryptDataEcb(uuidTmp);
        } catch (Exception e) {
            log.error("encrypt uuid error", e);
            throw new RuntimeException(e);
        }
        guestInfo.setUuid(uuid);
        guestInfo.setNotes(requestInfo.getNote());
        // 插入数据库
        guestInfoDao.insert(guestInfo);
        return guestInfo.getUuid();
    }
}
