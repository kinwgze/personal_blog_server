package zeee.blog.operlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2022/5/18 19:52
 * @description：操作日志实体
 */
@TableName("TBL_OPERLOG")
public class OperationLog implements Serializable {

    public static final Integer RESULT_SUCCESS = 0;
    public static final Integer RESULT_FAILURE = 1;
    public static final Integer RESULT_PARTIAL_SUCCESS = 2;

    private static final long serialVersionUID = -6160390565262710986L;

    @TableId(type = IdType.AUTO)
    private Long id = null;

    @TableField("login_name")
    private String loginName = null;

    @TableField("user_name")
    private String userName = null;

    @TableField("oper_time")
    private Date operTime;

    @TableField("address")
    private String address = null;

    @TableField("category")
    private Integer category = null;

    @TableField("description")
    private String description = null;

    @TableField("result")
    private Integer result = null;

    @TableField("failure_reason")
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
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
