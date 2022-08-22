package zeee.blog.common.loghttp;

import java.io.Serializable;

/**
 * @author wz
 * @date 2022/8/22
 */
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "LogHttpData{" +
                "id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
