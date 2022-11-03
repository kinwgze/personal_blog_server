package zeee.blog.operlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2022/5/18 19:52
 * @description：操作日志实体
 */
@Data
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

}
