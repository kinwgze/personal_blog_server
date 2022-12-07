package zeee.blog.common.redis;

/**
 * @author wz
 * @date 2022/12/7
 * redis的封装，具体方式需要实现接口
 */
public interface RedisClient {

    /**
     * 设置过期时间
     * @param key key
     * @param time time
     * @return 是否成功
     */
    boolean expire(String key, long time);

    /**
     * 通过key获取value
     * @param key key
     * @return Object
     */
    Object get(String key);

    /**
     * 向redis中写数据
     * @param key key
     * @param value value
     * @return 是否成功
     */
    boolean set(String key, Object value);

    /**
     * 向redis中写数据并设置时间
     * @param key key
     * @param value value
     * @param time time
     * @return 是否成功
     */
    boolean set(String key, Object value, long time);

    /**
     * 删除缓存
     * @param key key
     */
    void del(String key);

}
