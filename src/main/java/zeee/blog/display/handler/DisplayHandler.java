package zeee.blog.display.handler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zeee.blog.display.entity.MdNamePathVO;
import zeee.blog.display.dao.DisplayDao;
import zeee.blog.common.operlog.service.OperlogService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static zeee.blog.common.operlog.entity.OperationLog.RESULT_FAILURE;
import static zeee.blog.common.operlog.entity.OperationLog.RESULT_SUCCESS;


/**
 * @author wz
 * @date 2022/8/4
 */
@Service("displayHandler")
public class DisplayHandler {

    /**
     * 日志记录对象
     */
    private static final Logger log = LoggerFactory.getLogger(DisplayHandler.class);

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
    private DisplayDao displayService;

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


    public String getMarkdownContent(String filePath, Integer category) {

        if (StringUtils.isEmpty(filePath) || category == null) {
            return null;
        }
        String operDesc = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(category).append("_").append(filePath);
            // 尝试从redis获取数据，如果能够获取，就直接返回
            String fileContent = (String) redisTemplate.opsForValue().get(sb.toString());
            if (StringUtils.isNotEmpty(fileContent)) {
                operDesc = "get content from redis! ";
                log.info(operDesc);
                return fileContent;
            } else {
                // 如果获取不到，从数据库查询
                String content = displayService.getContentByPathAndCategory(filePath, category);
                if (StringUtils.isNotEmpty(content)) {
                    operDesc = "get content from db! ";
                    log.info(operDesc);
                    redisTemplate.opsForValue().set(sb.toString(), content);
                    return content;
                } else {
                    operDesc = "get content from db error!";
                    log.error(operDesc);
                }
            }
            operlogService.addLog(null, null, new Date(), null, category, operDesc,
                    RESULT_SUCCESS, null);
            return null;

        } catch (Exception e) {
            operDesc = "get markdown content error！";
            log.error(operDesc, e);
            operlogService.addLog(null, null, new Date(), null, category, operDesc,
                    RESULT_FAILURE, e.getMessage());
            throw e;
        }

    }
}
