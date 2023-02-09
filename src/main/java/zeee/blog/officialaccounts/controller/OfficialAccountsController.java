package zeee.blog.officialaccounts.controller;

import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import zeee.blog.common.loghttp.LogHttp;
import zeee.blog.officialaccounts.entity.BaseMessage;
import zeee.blog.officialaccounts.entity.BaseResponse;
import zeee.blog.officialaccounts.entity.TextMessage;
import zeee.blog.officialaccounts.entity.TextResponse;
import zeee.blog.officialaccounts.handler.AccountHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author zeeew
 * @Date 2023/2/7 19:39
 * @Description 微信公众号的消息接收模块
 */
@Api(tags = "微信消息接收")
@RestController
@RequestMapping("/official/account")
public class OfficialAccountsController {

    private static final String TOKEN = "WX692C1DA5ACC99BC6";

    public static final String AES_KEY = "xmfFdyJV4WICICGfIK1Xcj8tmJNDV9UXzr17uyIucO3";

    public static final Logger log = LoggerFactory.getLogger(OfficialAccountsController.class);

    @Resource
    private AccountHandler accountHandler;

    @LogHttp
    @ApiOperation(value = "微信校验接口，一般使用，修改微信公众号配置时，使用")
    @RequestMapping(method = RequestMethod.GET)
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        List<String> list = new ArrayList<String>(){{
            add(timestamp);
            add(nonce);
            add(TOKEN);
        }};
        log.info(String.format("signature is %s; \n timestamp is %s; \n nonce is %s; \n echostr is %s;",
                signature, timestamp, nonce, echostr));
        Collections.sort(list);
        String s = String.join("", list);
        log.info("origin string is:" + s);
        String sha1str = SecureUtil.sha1(s);
        log.info("sha1str string is:" + sha1str);
        if (Objects.nonNull(sha1str) && sha1str.equals(signature)) {
            return echostr;
        } else {
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
    public Object receiveMessage(HttpServletRequest req) {
        return accountHandler.handlerMessage(req);
    }
}
