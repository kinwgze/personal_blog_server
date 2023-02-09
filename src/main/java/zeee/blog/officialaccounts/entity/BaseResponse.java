package zeee.blog.officialaccounts.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2023/2/9 11:31
 * @Description
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -1452710411727471181L;

    /**
     * 接收方账号，收到的OpenID
     */
    @XmlElement(name = "ToUserName")
    private String toUserName;

    /**
     * 开发者微信号
     */
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间
     */
    @XmlElement(name = "CreateTime")
    private Long createTime;

    /**
     * 消息类型，文本为text
     */
    @XmlElement(name = "MsgType")
    private String msgType;

}
