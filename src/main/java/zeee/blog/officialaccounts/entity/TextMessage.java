package zeee.blog.officialaccounts.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2023/2/7 20:26
 * @Description 微信传过来的消息实体
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessage implements Serializable {

    private static final long serialVersionUID = 3878988951158524780L;

    /**
     * 开发者微信号
     */
    @XmlElement(name = "ToUserName")
    private String toUserName;

    /**
     * 发送方账号（一个OpenID）
     */
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间（整型）
     */
    @XmlElement(name = "CreateTime")
    private Long createTime;

    /**
     * 消息类型，文本为text
     */
    @XmlElement(name = "MsgType")
    private String msgType;

    /**
     * 文本消息内容
     */
    @XmlElement(name = "Content")
    private String content;

    /**
     * 消息id，64位整型
     */
    @XmlElement(name = "MsgId")
    private Long msgId;

    /**
     * 消息的数据ID（消息如果来自文章时才有）
     */
    @XmlElement(name = "MsgDataId")
    private Long msgDataId;

    /**
     * 多图文时的第几篇文章，从1开始（消息如果来自文章时才有）
     */
    @XmlElement(name = "Idx")
    private Long idx;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgDataId() {
        return msgDataId;
    }

    public void setMsgDataId(Long msgDataId) {
        this.msgDataId = msgDataId;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "OAMessage{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", msgId=" + msgId +
                ", msgDataId=" + msgDataId +
                ", idx=" + idx +
                '}';
    }
}
