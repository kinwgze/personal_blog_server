package zeee.blog.wechat.entity.weChat;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2022/12/28 18:44
 * @Description
 */
@Data
public class WeChatPushMessage implements Serializable {
    private static final long serialVersionUID = -6824919529474177548L;

    /**
     * 接收者openid
     */
    private String touser;

    /**
     * 模板ID
     */
    @JsonProperty("template_id")
    private String templateId;

    /**
     * 防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
     */
    @JsonProperty("client_msg_id")
    private String clientMsgId;

    /**
     * 模板数据
     */
    private JSONObject data;

}
