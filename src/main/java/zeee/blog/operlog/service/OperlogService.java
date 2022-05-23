package zeee.blog.operlog.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface OperlogService {

    enum OperationResultEnum {
        /**
         * 成功、失败、部分成功
         */
        SUCCESS,
        FAILURE,
        PARTIAL_SUCCESS
    }

    @Transactional(rollbackFor = Exception.class)
    void addLog(String loginName, String userName, Date date, String address,
                Integer category, String description, OperationResultEnum result, String failureReason);

    /**
     * 添加失败的操作日志
     */
    @Transactional(rollbackFor = Exception.class)
    void addFailureLog(String loginName, String userName, Date date, String address,
                       Integer category, String description, String failureReason);

    /**
     * 添加成功的日志
     * @param loginName 登陆名
     * @param userName 用户名
     * @param date 日期
     * @param address ip地址
     * @param category 分类
     * @param description 描述
     */
    void addSuccessLog(String loginName, String userName, Date date, String address,
                       Integer category, String description);
}
