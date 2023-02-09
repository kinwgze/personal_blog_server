package zeee.blog.officialaccounts.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author zeeew
 * @Date 2023/2/9 11:41
 * @Description
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextResponse extends BaseResponse{

    /**
     * 回复的消息内容
     * 换行：在content中可以换行，微信客户端支持换行显示
     */
    @XmlElement(name = "Content")
    private String content;

    @Override
    public String toString() {
        return "TextResponse{" +
                "BaseMessage='" + super.toString() + '\'' +
                "content='" + content + '\'' +
                '}';
    }
}
