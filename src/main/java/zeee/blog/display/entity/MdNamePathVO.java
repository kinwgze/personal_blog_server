package zeee.blog.display.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/8/11
 */
@Data
public class MdNamePathVO implements Serializable {
    private static final long serialVersionUID = -4782997469203914510L;

    private String path;

    private String title;
}
