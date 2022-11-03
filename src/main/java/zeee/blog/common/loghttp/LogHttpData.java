package zeee.blog.common.loghttp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/8/22
 */
@Data
public class LogHttpData implements Serializable {
    private static final long serialVersionUID = -2232299199533695300L;

    public static final String NAME = "HTTP_LOG";

    /**
     * http request的id
     */
    private String id;

    /**
     * http request的开始时间
     */
    private Long startTime;

    /**
     * 请求的url
     */
    private String url;

    /**
     * 请求的method
     */
    private String method;
}
