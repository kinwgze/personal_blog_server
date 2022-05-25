package zeee.blog.git.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.git.handler.SyncGitHandler;
import zeee.blog.operlog.service.OperlogService;
import zeee.blog.rpc.RpcResult;

import javax.annotation.Resource;

import static zeee.blog.operlog.entity.OperationLog.RESULT_SUCCESS;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 20:09
 * @description：与git之间进行同步
 */
@RestController
@RequestMapping("/blog/git")
public class SyncGitController {

    @Resource
    private SyncGitHandler syncGitHandler;

    @Resource
    private OperlogService operlog;

//    final String BUILD_WEBSITE_FROM_ZERO = "https://github.com/kinwgze/build_website_from_zero.git";


    /**
     * 日志记录对象
     */
    private static final Logger log = LoggerFactory.getLogger(SyncGitController.class);

    @RequestMapping(value = "syncFromGit", method = RequestMethod.GET)
    public RpcResult<Integer> cloneFromGit(@RequestParam(value = "url") String url){
        Integer res = null;
        if (url == null) {
            log.error("The received url from front is null!");
            return new RpcResult<>();
        }
        try {
            res = syncGitHandler.cloneGit(url);
        } catch (Exception e) {
           log.error(null, e);
        }
        return new RpcResult<Integer>(res);
    }

    @RequestMapping(value = "initDir", method = RequestMethod.GET)
    public RpcResult<String> initDir() {
        String desPath = "/var/git/build_website_from_zero";
        Integer result = syncGitHandler.initDir(desPath, 0);
        if (result.equals(RESULT_SUCCESS)) {
            return new RpcResult<>("success");
        } else {
            return new RpcResult<>("false");
        }
    }

}
