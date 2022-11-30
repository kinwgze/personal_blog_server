package zeee.blog.guardsystem.dao;

import zeee.blog.guardsystem.entity.GuardConstants;
import zeee.blog.guardsystem.entity.GuestDO;

import java.util.List;

/**
 * @author wz
 * @date 2022/11/30
 */
public interface GuestDao {

    /**
     * 通过手机号查询访客
     * @param phoneNumber 手机号
     * @return 访客，list
     */
    List<GuestDO> queryGuestByPhoneNumber(String phoneNumber);

    /**
     * 插入新访客信息
     * @param guestDo 访客信息
     */
    void insert(GuestDO guestDo);

    /**
     * 更新访客访问次数
     * @param guestDO 访客信息
     */
    void updateById(GuestDO guestDO);
}
