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
}
