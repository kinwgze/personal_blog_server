package zeee.blog.officialaccounts.handler;

import cn.hutool.json.JSONUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.officialaccounts.entity.BaseMessage;
import zeee.blog.officialaccounts.entity.TextMessage;
import zeee.blog.officialaccounts.entity.TextResponse;
import zeee.blog.utils.JsonUtil;
import zeee.blog.wechat.entity.tian.DailySentenceData;
import zeee.blog.wechat.service.WeChatService;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zeeew
 * @Date 2023/2/8 19:46
 * @Description
 */
@Service("accountHandler")
public class AccountHandler {

    private static final Logger log = LoggerFactory.getLogger(AccountHandler.class);

    public static final String DAILY_SENTENCE = "每日一句";

    @Resource
    private WeChatService weChatService;

    /**
     * 处理接收到的消息
     */
    public Object handlerMessage(HttpServletRequest req) {
        HashMap<String,String> map = new HashMap<>(8);
        try {
            // dom4j 用于读取XML 文件输入流的类
            ServletInputStream servletInputStream = req.getInputStream();
            // 读取XML文件输入流，XML文档对象
            SAXReader saxReader = new SAXReader();
            // XML文件根节点
            Document document = saxReader.read(servletInputStream);
            // 所有的子节点
            Element root = document.getRootElement();
            List<Element> childrenElement = root.elements();
            for (Element element : childrenElement) {
                map.put(element.getName(), element.getStringValue());
            }
            log.info("received message form wechat official account, message is \n" + map);
            // 如果是文本消息，进入处理文本消息的方法
            if (BaseMessage.TEXT_MESSAGE.equals(map.get(BaseMessage.MSG_TYPE))) {
                return handlerTextMessage(map);
            }
            // TODO other message types

        } catch (Exception e) {
            log.error("failed to handler message", e);
        }
        return "";
    }

    private TextResponse handlerTextMessage(HashMap<String,String> map) {
        TextMessage message = JsonUtil.jsonTOBean(JSONUtil.toJsonStr(map), TextMessage.class);
        TextResponse textResponse = new TextResponse();
        textResponse.setFromUserName(message.getToUserName());
        textResponse.setToUserName(message.getFromUserName());
        textResponse.setMsgType(message.getMsgType());
        textResponse.setCreateTime(message.getCreateTime());
        if (DAILY_SENTENCE.equals(message.getContent())) {
            DailySentenceData dailySentenceData = weChatService.getTianResult();
            textResponse.setContent(dailySentenceData.getRainBow());
        } else {
            textResponse.setContent(message.getContent());
        }
        return textResponse;
    }
}
