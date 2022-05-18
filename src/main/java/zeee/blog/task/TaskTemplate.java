package zeee.blog.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2022/5/17 19:19
 * @description：定时任务模板
 * 定时任务详见build_website_from_zero中blog_server开发日志
 */
@Component
@EnableScheduling
public class TaskTemplate {

    /**
     * 日志记录实体
     */
    private Logger log = LoggerFactory.getLogger(TaskTemplate.class);

    // 每天凌晨2:30触发
    @Scheduled(cron = "0 30 02 * * ?")
    public void doTask() {
        log.error("task start at" + new Date());
    }


}
