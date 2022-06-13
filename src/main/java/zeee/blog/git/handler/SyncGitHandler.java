package zeee.blog.git.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.Category;
import zeee.blog.exception.AppException;
import zeee.blog.exception.ErrorCodes;
import zeee.blog.git.entity.MarkDownFile;
import zeee.blog.git.service.MarkDownFileService;
import zeee.blog.git.utils.SyncBlogUtil;
import zeee.blog.operlog.service.OperlogService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static zeee.blog.operlog.entity.OperationLog.RESULT_FAILURE;
import static zeee.blog.operlog.entity.OperationLog.RESULT_SUCCESS;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 20:11
 * @description：
 */
@Service("SyncGitHandler")
public class SyncGitHandler {

    public static final int BUILD_WEBSITE_FROM_ZERO = 0;
    public static final int DAILY_LEARNING = 1;

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
            operlog.addLog(null, null, new Date(), null, Category.GIT,
                    "从" + url + "clone成功", RESULT_SUCCESS, null);
        } else {
            operlog.addLog(null, null, new Date(), null, Category.GIT,
                    "从" + url + "clone失败", RESULT_FAILURE, null);
        }
        return res;
    }

    /**
     * 读取指定目录下的MarkDown文件
     * @param path 指定目录path
     * @return 结果
     */
    public Integer initDir(String path, Integer category) {
        File sourceDir = new File(path);

        if (!sourceDir.exists()) {
            log.error("path not exist: " + path);
            operlog.addLog(null, null, new Date(), null, Category.GIT,
                    "path not exist: " + path, RESULT_FAILURE, null);
            return RESULT_FAILURE;
        }
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
            if (file != null && file.isFile() && StringUtils.endsWith(file.getName(), ".md")) {
                log.info("Write file " + file.getPath() + " to Database!");
                MarkDownFile mdFile = new MarkDownFile();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                mdFile.setDate(df.format(new Date(file.lastModified())));
                mdFile.setTitle(file.getName());
                mdFile.setSourceFilePath(file.getPath());
                mdFile.setCategory(category);
                // 读取内容
                mdFile.setMdFile(readMdFile(file.getPath()));
                // 转化为HTML
                mdFile.setHtmlFile(markDown2html(mdFile.getMdFile()));
                // 保存到数据库
                mdFileService.insert(mdFile);
            }
        }
        operlog.addLog(null, null, new Date(), null, Category.GIT,
                "Init dir success!", RESULT_SUCCESS, null);
        return RESULT_SUCCESS;
    }

    /**
     * 读取MarkDown文件的内容
     * @param filePath 文件路径
     * @return 文件内容
     */
    public String readMdFile(String filePath) {
        // 路径及目的文件判断
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        File markDownFile = new File(filePath);
        if (!markDownFile.exists() || markDownFile.isDirectory()) {
            return null;
        }

        // 读取文件，String类型
        try {
            log.info("Read file" + filePath);
            Path path = Paths.get(filePath);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return allLines.toString();
        } catch (IOException ie) {
            log.error(null, ie);
            throw new AppException(ErrorCodes.FILE_READ_ERROR, ErrorCodes.getErrorMessage(ErrorCodes.FILE_READ_ERROR));
        } catch (Exception e) {
            log.error(null, e);
            throw new AppException(ErrorCodes.UNKNOWN_ERROR);
        }

    }

    /**
     * 将MarkDown文件转化为HTML文件
     * @param markDown MarkDown文件的内容
     * @return 生成的HTML文件
     */
    public String markDown2html(String markDown) {
        // 将文件转换为html，string类型
        Parser parser = Parser.builder().build();
        Node document =  parser.parse(markDown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);

/*        // 如果目的文件存在，直接使用，如果目的文件不存在，新建文件
        File htmlFile = new File(desPath + fileName + ".html");
        boolean createHtmlFile;
        try {
            // 如果文件不存在，新建文件
            if (!htmlFile.exists()) {
                createHtmlFile = htmlFile.createNewFile();
            } else {
                // 如果文件存在，可以直接使用
                createHtmlFile = true;
            }
        } catch (IOException ie) {
            log.error("新建文件失败" + htmlFile.getPath(), ie);
            throw new AppException(ErrorCodes.CREATE_FILE_ERROR);
        }*/

/*        // 将html保存到目的文件，覆盖保存
        if (createHtmlFile) {
            // try-with-resource的形式，可以省去关闭资源的步骤
            try (FileWriter fileWriter = new FileWriter(htmlFile)) {
                fileWriter.write(html);
                return htmlFile.getPath();
            } catch (IOException ie) {
                log.error(null, ie);
                throw new AppException(ErrorCodes.FILE_WRITE_ERROR);
            }
        }*/
//        return null;
    }


}
