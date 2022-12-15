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
@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexData implements Serializable {
    private static final long serialVersionUID = 331762245001894661L;

    /**
     * 生活指数中文名称
     */
    String name;

    /**
     * 生活指数概要说明
     */
    String brief;

    /**
     * 生活指数详细说明
     */
    String detail;

}
