package zeee.blog.operlog.service;

import org.springframework.stereotype.Service;
import zeee.blog.operlog.dao.OperlogDO;
import zeee.blog.operlog.entity.OperationLog;

import javax.annotation.Resource;
import java.util.Date;

import static zeee.blog.operlog.entity.OperationLog.*;

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
    public void addSuccessLog(String loginName, String userName, Date date, String address,
                              String category, String description, OperationResultEnum result, String failureReason) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLoginName(loginName);
        operationLog.setUserName(userName);
        operationLog.setOperTime(new Date());
        operationLog.setAddress(address);
        operationLog.setCategory(category);
        operationLog.setDescription(description);
        operationLog.setResult(result == OperationResultEnum.SUCCESS ? RESULT_SUCCESS :
                (result == OperationResultEnum.FAILURE ? RESULT_FAILURE : RESULT_PARTIAL_SUCCESS));
        operationLog.setFailureReason(failureReason);
        operlogDO.addLog(operationLog);
    }
}
