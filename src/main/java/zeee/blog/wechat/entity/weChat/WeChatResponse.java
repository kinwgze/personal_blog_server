package zeee.blog.wechat.entity.weChat;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2022/12/28 18:42
 * @Description
 */
@Data
public class WeChatResponse implements Serializable {
    private static final long serialVersionUID = -6166023654717011429L;

    private Integer errcode;

    private String errmsg;

    private Long msgid;
}
