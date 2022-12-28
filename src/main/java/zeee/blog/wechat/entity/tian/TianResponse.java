package zeee.blog.wechat.entity.tian;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2022/12/28 17:51
 * @Description
 */
@Data
public class TianResponse implements Serializable {
    private static final long serialVersionUID = 4511106773366078223L;

    private Integer code;

    private String msg;

    private TianResult result;

}
