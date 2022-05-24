package zeee.blog.operlog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeee.blog.operlog.dao.OperlogDO;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2022/5/18 20:12
 * @description：
 */
@Service("OperlogService")
public class OperlogServiceImpl implements OperlogService{

    @Resource
    private OperlogDO operlogDO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLog(String loginName, String userName, Date date, String address,
                              Integer category, String description, Integer result, String failureReason) {

        operlogDO.addLog(loginName, userName, date.toString(), address, category, description, result, failureReason);
    }


}
