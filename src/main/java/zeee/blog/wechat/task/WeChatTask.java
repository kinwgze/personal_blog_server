package zeee.blog.wechat.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zeee.blog.wechat.service.WeChatService;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/12/13
 */
@Component
@EnableScheduling
public class WeChatTask {
    @Resource
    private WeChatService weChatService;
    /**
     * 每天早上8点推送到微信
     */
    @Scheduled(cron = "0 0 8 ? * * ")
    public void pushMessage(){
        weChatService.pushMessage();
    }

}
