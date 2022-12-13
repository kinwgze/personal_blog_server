package zeee.blog.guardsystem.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.guardsystem.entity.GuestVisitInfoDO;
import zeee.blog.guardsystem.mapper.GuestVisitInfoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wz
 * @date 2022/11/21
 */
@Service("guestInfoDao")
public class GuestVisitInfoDaoImpl implements GuestVisitInfoDao {

    @Resource
    private GuestVisitInfoMapper guestVisitInfoMapper;

    /**
     * 插入信息
     */
    @Override
    public void insert(GuestVisitInfoDO guestVisitInfoDO) {
        guestVisitInfoMapper.insert(guestVisitInfoDO);
    }

    /**
     * 更新信息
     *
     * @param guestVisitInfoDO guestVisitInfo
     */
    @Override
    public void update(GuestVisitInfoDO guestVisitInfoDO) {
        guestVisitInfoMapper.updateById(guestVisitInfoDO);
    }

    /**
     * 通过手机号和校验码查询访客访问信息
     *
     * @param phoneNumber 手机号
     * @param checkCode   校验码
     * @return 访客访问信息
     */
    @Override
    public List<GuestVisitInfoDO> queryGuestVisitInfoByPhoneNumberAndCheckCode(String phoneNumber, String checkCode) {
        QueryWrapper<GuestVisitInfoDO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", phoneNumber).eq("check_code", checkCode);
        return guestVisitInfoMapper.selectList(wrapper);
    }

    /**
     * 通过uuid查询visit信息，uuid一般都是唯一，不考虑多个查询结果的情况
     *
     * @param uuid uuid
     * @return visit info
     */
    @Override
    public GuestVisitInfoDO queryGuestVisitInfoByUuid(String uuid) {
        QueryWrapper<GuestVisitInfoDO> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid", uuid);
        return guestVisitInfoMapper.selectOne(wrapper);
    }
}
