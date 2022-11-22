package zeee.blog.guardsystem.dao;

import org.springframework.stereotype.Service;
import zeee.blog.guardsystem.entity.GuestInfo;
import zeee.blog.guardsystem.mapper.GuestInfoMapper;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/11/21
 */
@Service("guestInfoDao")
public class GuestInfoDaoImpl implements GuestInfoDao{

    @Resource
    private GuestInfoMapper guestInfoMapper;

    /**
     * 插入信息
     */
    @Override
    public void insert(GuestInfo guestInfo) {
        guestInfoMapper.insert(guestInfo);
    }
}
