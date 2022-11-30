package zeee.blog.guardsystem.dao;

import org.springframework.stereotype.Service;
import zeee.blog.guardsystem.entity.GuestVisitInfoDO;
import zeee.blog.guardsystem.mapper.GuestVisitInfoMapper;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/11/21
 */
@Service("guestInfoDao")
public class GuestVisitInfoDaoImpl implements GuestVisitInfoDao {

    @Resource
    private GuestVisitInfoMapper guestInfoMapper;

    /**
     * 插入信息
     */
    @Override
    public void insert(GuestVisitInfoDO guestVisitInfoDO) {
        guestInfoMapper.insert(guestVisitInfoDO);
    }
}
