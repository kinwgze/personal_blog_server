package zeee.blog.display.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import zeee.blog.display.entity.MdNamePathVO;
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
    public RpcResult<List<MdNamePathVO>> getNameListByCategory(@PathVariable(value = "category") Integer category) {
        RpcResult<List<MdNamePathVO>> result = new RpcResult<>();
        List<MdNamePathVO> res = displayHandler.getNameListByCategory(category);
        result.setData(res);
        return result;
    }

    @RequestMapping(value = "getMarkdownContent", method = RequestMethod.GET)
    public RpcResult<String> getMarkdownContent(@RequestParam(value = "filePath") String filePath,
                                                @RequestParam(value = "category") Integer category) {
        RpcResult<String> result = new RpcResult<>();
        String res = displayHandler.getMarkdownContent(filePath, category);
        result.setData(res);
        return result;
    }

}
