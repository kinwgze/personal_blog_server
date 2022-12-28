package zeee.blog.wechat.entity.baidu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author wz
 * @date 2022/12/14
 */
@Data
public class BaiduMapEntity implements Serializable {

    private static final long serialVersionUID = -5634832984516941630L;

    /**
     * 地理位置信息
     */
    AddressData location;

    /**
     * 实况数据
     */
    NowData now;

    /**
     * 气象预警数据
     */
    List<AlertData> alert;

    /**
     * 气象预警数据
     */
    List<IndexData> indexes;

    /**
     * 当天天气
     */
    List<ForecastData> forecasts;

}
