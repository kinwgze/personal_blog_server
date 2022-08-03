package zeee.blog.display.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.rpc.RpcListLoadResult;
import zeee.blog.rpc.RpcResult;

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


    @RequestMapping("getNameListByCategory/{category}")
    public RpcResult<List<String>> getNameListByCategory(@PathVariable(value = "category") Integer category) {
        RpcResult<List<String>> result = new RpcResult<>();
        // 根据category 尝试在redis中获取blog的nameList

        // 如果redis中获取不到，去数据库查询，结果存入redis

        return result;
    }

}
