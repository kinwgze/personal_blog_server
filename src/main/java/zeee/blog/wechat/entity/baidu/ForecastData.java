package zeee.blog.wechat.entity.baidu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author wz
 * @date 2022/12/14
 */
@Data
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastData implements Serializable {
    private static final long serialVersionUID = -1541656053277660838L;

    /**
     * 日期，北京时区
     */
    String date;

    /**
     * 星期，北京时区
     */
    String week;

    /**
     * 最高温度(℃)
     */
    Integer high;

    /**
     * 最低温度(℃)
     */
    Integer low;

    /**
     * 白天风力
     */
    @XmlElement(name = "wc_day")
    String wcDay;

    /**
     * 晚上风力
     */
    @XmlElement(name = "wc_night")
    String wcNight;

    /**
     * 白天风向
     */
    @XmlElement(name = "wd_day")
    String wdDay;

    /**
     * 晚上风向
     */
    @XmlElement(name = "wd_night")
    String wdNight;

    /**
     * 白天天气现象
     */
    @XmlElement(name = "text_day")
    String textDay;

    /**
     * 白天天气现象
     */
    @XmlElement(name = "text_night")
    String textNight;

}
