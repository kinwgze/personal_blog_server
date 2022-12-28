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
public class AlertData implements Serializable {

    private static final long serialVersionUID = 5644321444696546829L;

    /**
     * 预警事件类型
     */
    String type;

    /**
     * 预警事件等级
     */
    String level;

    /**
     * 预警标题
     */
    String title;

    /**
     * 预警详细提示信息
     */
    String desc;

}
