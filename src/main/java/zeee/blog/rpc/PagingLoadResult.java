package zeee.blog.rpc;


/**
 * @author zeee
 * 2022/4/13
 */
public interface PagingLoadResult<D> extends ListLoadResult<D> {

    /**
     * 获取分页的offset
     * @return
     */
    public int getOffset();

    /**
     * 获取数据总的size
     * @return
     */
    public int getTotalLength();

    /**
     * 设置offset
     * @param offset
     */
    public void setOffset(int offset);

    /**
     * 设置总的size
     * @param totalLength
     */
    public void setTotalLength(int totalLength);
}
