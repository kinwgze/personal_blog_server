package zeee.blog.git.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.Category;
import zeee.blog.git.utils.SyncBlogUtil;
import zeee.blog.operlog.service.OperlogService;

import javax.annotation.Resource;
import java.util.Date;

import static zeee.blog.operlog.entity.OperationLog.RESULT_SUCCESS;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 20:11
 * @description：
 */
@Service("SyncGitHandler")
public class SyncGitHandler {

    @Resource
    private SyncBlogUtil syncBlogUtil;

    @Resource
    private OperlogService operlog;

    /**
     * 日志记录实体
     */
    private static Logger log = LoggerFactory.getLogger(SyncGitHandler.class);


    public Integer cloneGit(String url){
        log.info("sync from " + url);
        Integer res = null;
        try {
            res = syncBlogUtil.gitCloneFromGithub(url);
        } catch (Exception e) {
            log.error(null, e);
        }
        // 记录操作日志
        if (res != null && res.equals(RESULT_SUCCESS)) {
            operlog.addSuccessLog(null, null, new Date(), null, Category.GIT,
                    "从" + url + "clone成功");
        } else {
            operlog.addFailureLog(null, null, new Date(), null, Category.GIT,
                    "从" + url + "clone失败", res == null ? null : res.toString());
        }
        return res;
    }
}
