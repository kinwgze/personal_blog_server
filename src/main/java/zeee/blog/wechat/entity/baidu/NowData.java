package zeee.blog.wechat.entity.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NowData implements Serializable {
    private static final long serialVersionUID = -5611475905115502860L;

    /**
     * 温度（℃）
     * 异常时返回“999999”
     */
    Integer temp;

    /**
     * 体感温度（℃）
     * 异常时返回“999999”
     */
    @JsonProperty("feels_like")
    Integer feelsLike;

    /**
     * 相对湿度（℃）
     * 异常时返回“999999”
     */
    Integer rh;

    /**
     * 风力等级
     * 异常时返回“暂无”
     */
    @JsonProperty("wind_class")
    String windClass;

    /**
     * 风向描述
     * 异常时返回“暂无”
     */
    @JsonProperty("wind_dir")
    String windDir;

    /**
     * 天气现象
     * 异常时返回“暂无”
     */
    String text;

    /**
     * 1小时累计降水量(mm)
     * 异常时返回“999999”
     * 高级字段
     */
    @JsonProperty("prec_1h")
    Double predOneHour;

    /**
     * 云量(%)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer clouds;

    /**
     * 空气质量指数数值
     * 异常时返回“999999”
     * 高级字段
     */
    Integer aqi;

    /**
     * pm2.5浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer pm25;

    /**
     * pm10浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer pm10;

    /**
     * 二氧化氮浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer no2;

    /**
     * 二氧化硫浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer so2;

    /**
     * 臭氧浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer o3;

    /**
     * 一氧化碳浓度(μg/m3)
     * 异常时返回“999999”
     * 高级字段
     */
    Integer co;

    /**
     * 数据更新时间
     * 北京时间
     */
    String uptime;


}
