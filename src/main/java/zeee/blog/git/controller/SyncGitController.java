package zeee.blog.git.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zeee.blog.git.handler.SyncGitHandler;
import zeee.blog.rpc.RpcResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    final String BUILD_WEBSITE_FROM_ZERO = "https://github.com/kinwgze/build_website_from_zero.git";


    /**
     * 日志记录对象
     */
    private static Logger log = LoggerFactory.getLogger(SyncGitController.class);

    @RequestMapping(value = "syncFromGit", method = RequestMethod.GET)
    public RpcResult<Integer> cloneFromGit(
            HttpSession session,
            @RequestParam(value = "url") String url){
        Integer res = null;
        try {
            res = syncGitHandler.cloneGit(url);
            log.info("sync from " + url + "success");
        } catch (Exception e) {
           log.error(null, e);
        }
        return new RpcResult<Integer>(res);
    }

}
