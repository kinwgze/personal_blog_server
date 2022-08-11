package zeee.blog.display.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zeee.blog.display.entity.MdNamePathVO;
import zeee.blog.display.service.DisplayService;
import zeee.blog.operlog.service.OperlogService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static zeee.blog.operlog.entity.OperationLog.RESULT_FAILURE;
import static zeee.blog.operlog.entity.OperationLog.RESULT_SUCCESS;


/**
 * @author wz
 * @date 2022/8/4
 */
@Service("DisplayHandler")
public class DisplayHandler {

    /**
     * 日志记录对象
     */
    private final Logger log = LoggerFactory.getLogger(DisplayHandler.class);

    /**
     * 需要注入redis模板
     * 对于RedisTemplate的泛型情况,
     * 可以使用<String, String>
     * <Object, Object>
     * 或者不写泛型
     * 注意,属性的名称必须为redisTemplate,因为按名称注入,框架创建的对象就是这个名字的
     */
    @Resource
    private RedisTemplate redisTemplate;

//    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private DisplayService displayService;

    @Resource
    private OperlogService operlogService;


    @SuppressWarnings("unchecked")
    public List<MdNamePathVO> getNameListByCategory(Integer category) {
        try {
            // 尝试从redis获取，根据size判断是否有数据
            String key = "nameListCategory" + category;
            Long size = redisTemplate.opsForList().size(key);
            String operDesc = null;
            if (size == null || size == 0) {
                // 如果redis里面获取不到，尝试从数据库获取
                List<MdNamePathVO> namesFromDb = displayService.queryNameAndPathListByCategory(category);
                // 然后将数据库中获取到的数据放到redis中
                redisTemplate.opsForList().rightPushAll(key, namesFromDb);
                operDesc = "get name list by category form db! ";
                log.info(operDesc);
                return namesFromDb;
            }
            List<MdNamePathVO> names = redisTemplate.opsForList().range(key, 0, size);
            operDesc = "get name list by category form redis! ";
            log.info(operDesc);
            operlogService.addLog(null, null, new Date(), null, category, operDesc,
                    RESULT_SUCCESS, null);
            return names;
        } catch (Exception e) {
            String operDesc = "get name list by category error!";
            log.error(operDesc, e);
            operlogService.addLog(null, null, new Date(), null, category, operDesc,
                    RESULT_FAILURE, e.getMessage());
            throw e;
        }
    }


}
