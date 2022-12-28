package zeee.blog.wechat.entity.baidu;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2022/12/28 16:00
 */
@Data
public class BaiduResponse implements Serializable {

    private static final long serialVersionUID = -6460497734949608051L;

    private BaiduMapEntity result;

    private Integer status;

    private String message;

}
