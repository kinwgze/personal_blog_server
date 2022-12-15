package zeee.blog.wechat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.wechat.entity.baidu.BaiduMapEntity;
import zeee.blog.wechat.service.WeChatService;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2022/12/14
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    // TODO 这里有问题，还没调通
    public BaiduMapEntity test() {
        return weChatService.getBaiduData();
    }
}
