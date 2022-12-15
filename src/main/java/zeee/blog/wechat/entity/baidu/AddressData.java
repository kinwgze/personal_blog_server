package zeee.blog.wechat.entity.baidu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author wz
 * @date 2022/12/14
 */
@Data
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressData implements Serializable {

    private static final long serialVersionUID = -434941948396956344L;

    /**
     * 国家名称
     */
    String country;

    /**
     * 省份名称
     */
    String province;

    /**
     * 城市名称
     */
    String city;

    /**
     * 区县名称
     */
    String name;

    /**
     * 区县id
     */
    String id;

}
