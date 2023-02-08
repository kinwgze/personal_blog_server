package zeee.blog.officialaccounts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author zeeew
 * @Date 2023/2/8 20:04
 * @Description 文本消息
 */
@Data
public class TextMessage extends BaseMessage {

    /**
     * 文本消息内容
     */
    @JsonProperty("Content")
    private String content;

}
