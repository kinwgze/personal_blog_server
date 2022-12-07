package zeee.blog.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wz
 * @date 2022/12/7
 */
@Service("redisClient")
public class RedisClientImpl implements RedisClient{

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(RedisClientImpl.class);

    public RedisClientImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param time time
     * @return 是否成功
     */
    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("access redis error: ", e);
            return false;
        }
    }

    /**
     * 通过key获取value
     *
     * @param key key
     * @return Object
     */
    @Override
    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("access redis error: ", e);
            return null;
        }
    }

    /**
     * 向redis中写数据
     *
     * @param key   key
     * @param value value
     * @return 是否成功
     */
    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("access redis error: ", e);
            return false;
        }
    }

    /**
     * 向redis中写数据并设置时间
     *
     * @param key   key
     * @param value value
     * @param time  time
     * @return 是否成功
     */
    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("access redis error: ", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key key
     */
    @Override
    public void del(String key) {
        if (key == null) {
            return;
        }
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("access redis error: ", e);
        }
    }
}
