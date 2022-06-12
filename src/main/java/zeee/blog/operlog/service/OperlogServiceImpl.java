package zeee.blog.operlog.service;

import org.springframework.stereotype.Service;
import zeee.blog.operlog.dao.OperlogDO;
import zeee.blog.operlog.entity.OperationLog;

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
    public void addLog(String loginName, String userName, Date date, String address,
                              Integer category, String description, Integer result, String failureReason) {

        OperationLog operationLog = new OperationLog();
        operationLog.setLoginName(loginName);
        operationLog.setUserName(userName);
        operationLog.setOperTime(date);
        operationLog.setAddress(address);
        operationLog.setCategory(category);
        operationLog.setDescription(description);
        operationLog.setResult(result);
        operationLog.setFailureReason(failureReason);
        operlogDO.insert(operationLog);
    }


}
