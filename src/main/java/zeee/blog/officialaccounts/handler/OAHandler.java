package zeee.blog.officialaccounts.handler;

import cn.hutool.json.JSONUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.officialaccounts.entity.BaseMessage;
import zeee.blog.officialaccounts.entity.TextMessage;
import zeee.blog.utils.JsonUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zeeew
 * @Date 2023/2/8 19:46
 * @Description
 */
@Service("oaHandler")
public class OAHandler {

    private static final Logger log = LoggerFactory.getLogger(OAHandler.class);

    /**
     * 将微信返回的数据转换为message对象
     */
    public BaseMessage parseXml2Message(HttpServletRequest req) {
        HashMap<String,String> map = new HashMap<>();
        BaseMessage message = null;
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
            if (BaseMessage.TEXT_MESSAGE.equals(map.get(BaseMessage.MSG_TYPE))) {
                String s = JSONUtil.toJsonStr(map);
                message = JsonUtil.jsonTOBean(s, TextMessage.class);
            }
            // TODO other message types

        } catch (Exception e) {
            log.error(null, e);
        }
        return message;
    }
}
