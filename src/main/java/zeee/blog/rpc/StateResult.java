package zeee.blog.rpc;
/**
 * @author ：wz
 * @date ：Created in 2022/4/7 19:07
 * @description：
 */
public class StateResult {

    public static final int SUCCESS = 0;

    public static final int FAILURE = 1;

    public static final int PARTIAL_SUCCESS = 2;

    private int state = 0;

    private int errorCode = 0;

    private String successMessage = null;

    private String failureMessage = null;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
