package zeee.blog.git.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.git.utils.SyncBlogUtil;

import javax.annotation.Resource;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 20:11
 * @description：
 */
@Service("SyncGitHandler")
public class SyncGitHandler {

    @Resource
    private SyncBlogUtil syncBlogUtil;

    /**
     * 日志记录
     */
    private static Logger log = LoggerFactory.getLogger(SyncGitHandler.class);


    public int cloneGit(String url){
        return syncBlogUtil.gitCloneFromGithub(url);
    }
}
