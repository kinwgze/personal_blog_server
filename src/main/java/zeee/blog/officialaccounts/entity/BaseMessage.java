package zeee.blog.officialaccounts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2023/2/7 20:26
 * @Description 微信传过来的消息实体
 */
@Data
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = 3878988951158524780L;

    public static final String TEXT_MESSAGE = "text";
    public static final String IMAGE_MESSAGE = "image";
    public static final String VOICE_MESSAGE = "voice";
    public static final String VIDEO_MESSAGE = "video";
    public static final String SHORTVIDEO_MESSAGE = "shortvideo";
    public static final String LOCATION_MESSAGE = "location";
    public static final String LINK_MESSAGE = "link";

    public static final String MSG_TYPE = "MsgType";


    /**
     * 开发者微信号
     */
    @JsonProperty("ToUserName")
    private String toUserName;

    /**
     * 发送方账号（一个OpenID）
     */
    @JsonProperty("FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间（整型）
     */
    @JsonProperty("CreateTime")
    private Long createTime;

    /**
     * 消息类型，
     * 文本为text
     * 图片为image
     * 语音为voice
     * 视频为video
     * 小视频为shortvideo
     * 地理位置为location
     * 链接为link
     */
    @JsonProperty("MsgType")
    private String msgType;

    /**
     * 消息id，64位整型
     */
    @JsonProperty("MsgId")
    private Long msgId;

    /**
     * 消息的数据ID
     * （消息如果来自文章时才有）
     */
    @JsonProperty("MsgDataId")
    private Long msgDataId;

//    /**
//     * 图片链接（由系统生成）
//     * 类型为image时才有
//     */
//    @XmlElement(name = "PicUrl")
//    private String picUrl;
//
//    /**
//     * 图片/语音消息媒体id，可以调用获取临时素材接口拉取数据。
//     * 类型为image、voice时才有
//     */
//    @XmlElement(name = "MediaId")
//    private Long mediaId;
//
//    /**
//     * 语音格式，如amr，speex等
//     * 类型为voice时才有
//     */
//    @XmlElement(name = "Format")
//    private String format;
//
//    /**
//     * 语音识别结果,UTF8编码
//     * 请注意，开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息 XML 数据包中，增加一个 Recognition 字段
//     * （注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，对已关注用户需要24小时生效。
//     * 开发者可以重新关注此帐号进行测试）
//     * 类型为voice时才有
//     */
//    @XmlElement(name = "Recognition")
//    private String recognition;
//
//    /**
//     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
//     * 类型为video、shortvideo时才有
//     */
//    @XmlElement(name = "ThumbMediaId")
//    private String thumbMediaId;
//
//    /**
//     * 地理位置纬度
//     * 类型为location时才有
//     */
//    @XmlElement(name = "Location_X")
//    private String location_X;
//
//    /**
//     * 地理位置经度
//     * 类型为location时才有
//     */
//    @XmlElement(name = "Location_Y")
//    private String location_Y;
//
//    /**
//     * 地图缩放大小
//     * 类型为location时才有
//     */
//    @XmlElement(name = "Scale")
//    private String scale;
//
//    /**
//     * 地理位置信息
//     * 类型为location时才有
//     */
//    @XmlElement(name = "Label")
//    private String label;
//
//    /**
//     * 消息标题
//     * 类型为url时才有
//     */
//    @XmlElement(name = "Title")
//    private String title;
//
//    /**
//     * 消息描述
//     * 类型为url时才有
//     */
//    @XmlElement(name = "Description")
//    private String description;
//
//    /**
//     * 消息链接
//     * 类型为url时才有
//     */
//    @XmlElement(name = "Url")
//    private String url;


//    /**
//     * 多图文时的第几篇文章，从1开始
//     * （消息如果来自文章时才有）
//     */
//    @XmlElement(name = "Idx")
//    private Long idx;

}
