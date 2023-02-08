package zeee.blog.officialaccounts.handler;

import cn.hutool.json.JSONUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import zeee.blog.officialaccounts.entity.BaseMessage;
import zeee.blog.officialaccounts.entity.TextMessage;
import zeee.blog.utils.JsonUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author zeeew
 * @Date 2023/2/8 19:46
 * @Description
 */
@Service("oaHandler")
public class OAHandler {

    public TextMessage parseXml2Message(HttpServletRequest req) {
        HashMap<String,String> map = new HashMap<>();
        TextMessage message = new TextMessage();
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
            if ("text".equals(map.get("MsgType"))) {
                String s = JSONUtil.toJsonStr(map);
                message = JSONUtil.toBean(s, TextMessage.class);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}
