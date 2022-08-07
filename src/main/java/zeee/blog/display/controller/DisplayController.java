package zeee.blog.display.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.display.handler.DisplayHandler;
import zeee.blog.rpc.RpcListLoadResult;
import zeee.blog.rpc.RpcResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wz
 * @date 2022/8/3
 */
@RestController
@RequestMapping("/blog/display")
public class DisplayController {

    /**
     * 日志记录对象
     */
    private static final Logger log = LoggerFactory.getLogger(DisplayController.class);

    @Resource
    private DisplayHandler displayHandler;


    @RequestMapping(value = "getNameListByCategory/{category}", method = RequestMethod.GET)
    public RpcResult<List<String>> getNameListByCategory(@PathVariable(value = "category") Integer category) {
        RpcResult<List<String>> result = new RpcResult<>();
        List<String> names = displayHandler.getNameListByCategory(category);
        result.setData(names);

        return result;
    }

}
