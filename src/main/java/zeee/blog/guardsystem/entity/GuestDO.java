package zeee.blog.guardsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/11/30
 */
@Data
@TableName("guest")
public class GuestDO implements Serializable {
    private static final long serialVersionUID = -276244971596372370L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 访客名
     */
    @TableField("name")
    private String name;

    /**
     * 访客手机号
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 访问次数
     */
    @TableField("statistics")
    private Integer statistics;



}
