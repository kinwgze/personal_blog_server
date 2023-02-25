package zeee.blog.guardsystem.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "姓名",example = "wang")
    private String guestName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "13300001111")
    private String phoneNumber;

    /**
     * 所申请的日期
     */
    @ApiModelProperty(value = "申请开始时间", example = "2023-1-1 02:20")
    private String startTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "test")
    private String note;
}
