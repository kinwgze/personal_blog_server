package zeee.blog.guardsystem.dao;

import zeee.blog.guardsystem.entity.GuestVisitInfoDO;

import java.util.List;

/**
 * @author wz
 * @date 2022/11/21
 */
public interface GuestVisitInfoDao {

    /**
     * 插入信息
     */
    void insert(GuestVisitInfoDO guestVisitInfoDO);

    /**
     * 通过手机号和校验码查询访客访问信息
     * @param phoneNumber 手机号
     * @param checkCode 校验码
     * @return 访客访问信息
     */
    List<GuestVisitInfoDO> queryGuestVisitInfoByPhoneNumberAndCheckCode(String phoneNumber, String checkCode);

}
