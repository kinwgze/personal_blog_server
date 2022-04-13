package zeee.blog.rpc;

/**
 * @author ：wz
 * @date ：Created in 2022/4/7 19:14
 * @description：
 */
public class RpcResult<D> extends StateResult {

    private D data = null;

    public RpcResult() {
    }

    public RpcResult(String successMessage) {
        setState(SUCCESS);
        setSuccessMessage(successMessage);
    }

    public RpcResult(D data) {
        setState(SUCCESS);
        this.data = data;
    }

    public RpcResult(D data, String successMessage) {
        setState(SUCCESS);
        this.data = data;
        setSuccessMessage(successMessage);
    }

    public RpcResult(String failureMessage, int errorCode) {
        setState(FAILURE);
        setErrorCode(errorCode);
        setFailureMessage(failureMessage);
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
