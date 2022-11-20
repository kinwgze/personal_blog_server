package zeee.blog.common.rpc;

import java.util.List;

/**
 * @author zeee
 * 2022/4/13
 */
public class RpcListLoadResult<D> extends StateResult implements ListLoadResult<D> {

    private List<D> data = null;

    protected RpcListLoadResult() {
    }

    public RpcListLoadResult(List<D> data) {
        setState(SUCCESS);
        this.data = data;
    }

    public RpcListLoadResult(List<D> data, String successMessage) {
        setState(SUCCESS);
        this.data = data;
        setSuccessMessage(successMessage);
    }

    public RpcListLoadResult(int errorCode, String failureMessage) {
        setState(FAILURE);
        setErrorCode(errorCode);
        setFailureMessage(failureMessage);
    }

    @Override
    public List<D> getData() {
        return null;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

}
