package zeee.blog.guardsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zeeew
 * @date 2022/11/20 19:16
 */
@Data
@TableName("TBL_GUEST_INFO")
public class GuestInfo implements Serializable {

    private static final long serialVersionUID = 6262071575153530517L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户手机号
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 申请的提交时间
     */
    @TableField("commit_time")
    private long commitTime;

    /**
     * 准许进入时间
     */
    @TableField("start_time")
    private long startTime;

    /**
     * 准许的结束时间
     */
    @TableField("end_time")
    private long endTime;

    /**
     * 事件UUID
     */
    @TableField("uuid")
    private String uuid;

    /**
     * 校验码
     */
    @TableField("check_code")
    private String checkCode;

    /**
     * 备注
     */
    @TableField("notes")
    private String notes;
}
