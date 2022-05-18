package zeee.blog.operlog.service;

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

    /**
     * 添加日志
     * @param loginName 登陆名
     * @param userName 用户名
     * @param date 日期
     * @param address ip地址
     * @param category 分类
     * @param description 描述
     * @param result 操作结果
     * @param failureReason 错误原因
     */
    void addSuccessLog(String loginName, String userName, Date date, String address,
                       String category, String description, OperationResultEnum result, String failureReason);
}
