package zeee.blog.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import zeee.blog.utils.FuncUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wz
 * @date ：Created in 2022/5/17 19:19
 * @description：定时任务模板
 */
@Component
public class TaskTemplate {

    /**
     * 日志记录实体
     */
    private Logger log = LoggerFactory.getLogger(TaskTemplate.class);

    @Resource
    private TemplateTask templateTask;

    ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    @PostConstruct
    private void init() {
        final long ONE_DAY = 24 * 60 * 60 * 1000;
        long initDay = FuncUtil.getTimeMillis("2:00:00") - System.currentTimeMillis();
        initDay = initDay > 0 ? initDay : ONE_DAY + initDay;
        scheduler.scheduleAtFixedRate(templateTask, initDay, ONE_DAY, TimeUnit.MILLISECONDS);
    }

    @Service("templateTask")
    class TemplateTask extends TimerTask {

        @Override
        public void run() {
            // 任务代码
            log.error("task start, time is" + new Date());
        }
    }

}
