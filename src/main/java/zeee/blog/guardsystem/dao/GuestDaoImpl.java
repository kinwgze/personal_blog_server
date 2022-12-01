package zeee.blog.guardsystem.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.guardsystem.entity.GuestDO;
import zeee.blog.guardsystem.mapper.GuestMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wz
 * @date 2022/11/30
 */
@Service("guestDao")
public class GuestDaoImpl implements GuestDao{

    @Resource
    private GuestMapper guestMapper;

    /**
     * 通过手机号查询访客
     * @param phoneNumber 手机号
     * @return 访客，list
     */
    @Override
    public List<GuestDO> queryGuestByPhoneNumber(String phoneNumber) {
        QueryWrapper<GuestDO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", phoneNumber);
        return guestMapper.selectList(wrapper);
    }

    /**
     * 插入新访客信息
     * @param guestDo 访客信息
     */
    @Override
    public void insert(GuestDO guestDo) {
        guestMapper.insert(guestDo);
    }

    /**
     * 更新访客访问次数
     * @param guestDO 访客信息
     */
    @Override
    public void updateById(GuestDO guestDO) {
        guestMapper.updateById(guestDO);
    }
}
