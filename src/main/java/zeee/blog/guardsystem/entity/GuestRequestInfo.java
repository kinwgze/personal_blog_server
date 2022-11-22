package zeee.blog.guardsystem.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/11/21
 */
@Data
@ApiModel(value = "GuestRequestInfo", description = "访客申请信息")
public class GuestRequestInfo implements Serializable {

    private static final long serialVersionUID = -4089279663169081000L;

    /**
     * 姓名
     */
    private String guestName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 所申请的日期
     */
    private long startTime;

    /**
     * 备注
     */
    private String note;
}
