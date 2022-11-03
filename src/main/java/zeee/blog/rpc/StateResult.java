package zeee.blog.rpc;

import lombok.Data;

/**
 * @author ：wz
 * @date ：Created in 2022/4/7 19:07
 * @description：
 */
@Data
public class StateResult {

    public static final int SUCCESS = 0;

    public static final int FAILURE = 1;

    public static final int PARTIAL_SUCCESS = 2;

    private int state = 0;

    private int errorCode = 0;

    private String successMessage = null;

    private String failureMessage = null;

}
