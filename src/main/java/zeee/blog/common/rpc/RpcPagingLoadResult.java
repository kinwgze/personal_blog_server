package zeee.blog.common.rpc;

import java.util.List;

/**
 * @author zeee
 * 2022/4/13
 */
public class RpcPagingLoadResult<D> extends RpcListLoadResult<D> implements PagingLoadResult<D>  {

    private long offset = 0;

    private long totalLength = 0;

    public RpcPagingLoadResult() {
    }

    public RpcPagingLoadResult(List<D> data) {
        super(data);
    }

    public RpcPagingLoadResult(List<D> data, long offset, long totalLength) {
        super(data);
        this.offset = offset;
        this.totalLength = totalLength;
    }

    public RpcPagingLoadResult(List<D> data, String successMessage) {
        super(data, successMessage);
    }

    public RpcPagingLoadResult(int errorCode, String failureMessage) {
        super(errorCode, failureMessage);
    }

    @Override
    public int getOffset() {
        return (int) offset;
    }

    @Override
    public int getTotalLength() {
        return (int) totalLength;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }
}
