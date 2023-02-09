package zeee.blog.wechat.entity.tian;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zeeew
 * @Date 2022/12/28 17:53
 * @Description
 */
@Data
public class DailySentenceResult implements Serializable {
    private static final long serialVersionUID = -709160602903284260L;

    private Integer id;

    private String content;

    private String note;

    private String source;

    private String date;
}
