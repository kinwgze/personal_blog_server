package zeee.blog.display.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wz
 * @date 2022/8/4
 */
@Service("DisplayHandler")
public class DisplayHandler {

    /**
     * 日志记录对象
     */
    private final Logger log =  LoggerFactory.getLogger(DisplayHandler.class);

    /**
     * 需要注入redis模板
     * 对于RedisTemplate的泛型情况,
     * 可以使用<String, String>
     *       <Object, Object>
     *       或者不写泛型
     *  注意,属性的名称必须为redisTemplate,因为按名称注入,框架创建的对象就是这个名字的
     */
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @SuppressWarnings("unchecked")
    public List<String> getNameListByCategory(Integer category) {
        // 尝试从redis获取
        String key = "nameListCategory"+category;
        // TODO 这里不应该使用object的形式，需要采用list的形式进行存储和读取。相对应的，update的方法里面，如果是
        // 新增了一个问题，需要对应的向redis中中添加，udpate结束并进行校验size，如果redis和db中的size一样，不需要处理，
        // 如果不一样，需要处理删掉重新建立缓存

        List<String> names = (List<String>) redisTemplate.opsForValue().get(key);



        // 如果获取不到
        if (CollectionUtils.isEmpty(names)) {
            // 尝试从数据库获取

            List<String> namesFromDb = new ArrayList<>();


            // 放到redis中
            // 操作Redis中的string类型的数据,先获取ValueOperation
            ValueOperations<String, List<String>> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, namesFromDb);

            return namesFromDb;
        }

        return names;


    }
}
