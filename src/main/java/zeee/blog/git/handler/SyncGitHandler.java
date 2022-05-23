package zeee.blog.git.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.Category;
import zeee.blog.git.entity.MarkDownFile;
import zeee.blog.git.service.MarkDownFileService;
import zeee.blog.git.utils.SyncBlogUtil;
import zeee.blog.operlog.service.OperlogService;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;

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

    @Resource
    private MarkDownFileService mdFileService;

    /**
     * 日志记录实体
     */
    private static Logger log = LoggerFactory.getLogger(SyncGitHandler.class);


    /**
     * 从指定的url clone git项目
     * @param url 项目地址
     * @return 结果，0——成功
     */
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

    /**
     * 读取指定目录下的MarkDown文件
     * @param path 指定目录
     * @return 结果
     */
    public Integer readDir(String path) {
        File sourceDir = new File(path);
        // 使用BFS遍历
        Deque<File> dirQueue = new ArrayDeque<>();
        dirQueue.add(sourceDir);
        while (CollectionUtils.isNotEmpty(dirQueue)) {
            File file = dirQueue.poll();
            // 如果是目录
            if (file != null && file.isDirectory()) {
                log.info("now is " + file.getName());
                File[] temp = file.listFiles();
                if (temp != null) {
                    dirQueue.addAll(Arrays.asList(temp));
                }
            }
            // 如果是文件，必定是md文件，以.md结尾，存放到数据库中
            if (file != null && file.isFile()) {
                MarkDownFile mdFile = new MarkDownFile();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                mdFile.setDate(df.format(new Date(file.lastModified())));
                mdFile.setTitle(file.getName());
                mdFile.setSourceFilePath(file.getPath());
                // 转化为HTML
                mdFile.setHtmlFilePath(null);
                // 读取内容
                mdFile.setText(null);
                mdFileService.addMarkDownFile(mdFile);
            }
        }


        if (sourceDir.exists()) {
            File[] tempList = sourceDir.listFiles();
            assert tempList != null;
            log.info(String.format("目录 %s 下共有 %d 个文件", path, tempList.length));
        }

        return 0;
    }
}
