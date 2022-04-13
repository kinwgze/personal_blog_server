package zeee.blog.rpc;

import java.util.List;

/**
 * @author zeee
 * 2022/4/13
 */
public interface ListLoadResult<D> {

    /**
     * 获取数据
     * @return
     */
    public List<D> getData();
}
