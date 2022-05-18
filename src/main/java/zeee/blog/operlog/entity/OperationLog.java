package zeee.blog.operlog.entity;

import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2022/5/18 19:52
 * @description：操作日志实体
 */
public class OperationLog {

    public static final Integer RESULT_SUCCESS = 0;
    public static final Integer RESULT_FAILURE = 1;
    public static final Integer RESULT_PARTIAL_SUCCESS = 2;

    private Long id = null;

    private String loginName = null;

    private String userName = null;

    private Date operTime;

    private String address = null;

    private String category = null;

    private String description = null;

    private Integer result = null;

    private String failureReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
