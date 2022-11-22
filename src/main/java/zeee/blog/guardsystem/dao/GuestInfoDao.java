package zeee.blog.guardsystem.dao;

import zeee.blog.guardsystem.entity.GuestInfo;

/**
 * @author wz
 * @date 2022/11/21
 */
public interface GuestInfoDao {

    /**
     * 插入信息
     */
    void insert(GuestInfo guestInfo);
}
