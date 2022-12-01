package zeee.blog.guardsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/11/30
 */
@Data
public class GuestResponseVO implements Serializable {

    private static final long serialVersionUID = 4367049310753195329L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 校验码，6位
     */
    private String checkCode;

    /**
     * 事件唯一uuid
     */
    private String uuid;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
