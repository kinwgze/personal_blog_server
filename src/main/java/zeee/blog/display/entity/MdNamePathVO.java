package zeee.blog.display.entity;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/8/11
 */
public class MdNamePathVO implements Serializable {
    private static final long serialVersionUID = -4782997469203914510L;

    private String path;

    private String title;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MdNamePathVO{" +
                "path='" + path + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
