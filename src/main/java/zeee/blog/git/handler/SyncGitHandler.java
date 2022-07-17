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
import zeee.blog.utils.FuncUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    final String BUILD_WEBSITE_FROM_ZERO_URL = "https://github.com/kinwgze/build_website_from_zero.git";

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
                MarkDownFile mdFile = readMdFileByPath(file.getPath(), category);
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
     * 根据文件路径和分类，读取文件，转为MarkDownFile类型
     * @param filePath
     * @param category
     * @return
     */
    public MarkDownFile readMdFileByPath(String filePath, Integer category) {
        MarkDownFile mdFile = new MarkDownFile();
        if (StringUtils.isNotEmpty(filePath)) {
            File file = new File(filePath);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            mdFile.setDate(df.format(new Date(file.lastModified())));
            mdFile.setTitle(file.getName());
            mdFile.setSourceFilePath(file.getPath());
            mdFile.setCategory(category);
            // 读取内容
            mdFile.setMdFile(readMdFile(file.getPath()));
            // 转化为HTML
            mdFile.setHtmlFile(markDown2html(mdFile.getMdFile()));
        }
        return mdFile;
    }

    /**
     * 将MarkDown文件转化为HTML文件
     * @param markDown MarkDown文件的内容
     * @return 生成的HTML文件
     */
    public String markDown2html(String markDown) {
        if (StringUtils.isEmpty(markDown)) {
            return null;
        }
        // 将文件转换为html，string类型
        Parser parser = Parser.builder().build();
        Node document =  parser.parse(markDown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }


    /**
     * 根据项目路径和分类，更新数据库记录
     * @param url
     * @param category
     * @return
     */
    public Integer updateGitProject(String url, int category) {
        String desPath = null;
        if (category == 0) {
            desPath = "/var/git/build_website_from_zero";
        } else if (category == 1) {
            desPath = "/var/git/daily_learning";
        }
        AtomicInteger sum = new AtomicInteger();
        try {
            if (desPath != null) {
                // git fetch
                String fetchResult = null;
                try {
                    fetchResult = FuncUtil.runCommandThrowException(new String[]{"/bin/sh", "-c", "git fetch"},
                            null, new File(desPath), 10 * 1000);
                } catch (Exception e) {
                    log.error(null, e);
                    throw new AppException(ErrorCodes.GIT_FETCH_ERROR);
                }
                // 获取diff信息
                String result = null;
                if (fetchResult != null) {
                     result = FuncUtil.runCommandThrowException(new String[]{"/bin/sh", "-c", "git diff origin/main"},
                            null, new File(desPath), 100 * 1000);
                }
                // git pull
                String pullResult = FuncUtil.runCommandThrowException(new String[]{"/bin/sh", "-c", "git pull"},
                        null, new File(desPath), 100 * 1000);
                // 匹配diff信息中的md文件，保存在mdFileSet中
                Set<String> mdFileSet = new HashSet<>();
                if (StringUtils.isNotEmpty(result)) {
                    final String REGEX_MD = "(/)([a-zA-Z0-9_./]+)\\.(md)";
                    Pattern pattern = Pattern.compile(REGEX_MD);
                    Matcher matcher = pattern.matcher(result);
                    while (matcher.find()) {
                        // 找到的格式为/blog_server/1.testf_test.md
                        String fileName = matcher.group();
                        String filePath = desPath + fileName;
                        mdFileSet.add(filePath);
                    }
                }
                if (CollectionUtils.isNotEmpty(mdFileSet)) {
                    // 根据category读取文件
                    List<MarkDownFile> files = mdFileService.selectByCategory(category);
                    Set<String> filePathSet = new HashSet<>();
                    // 拿到所有文件的路径
                    files.forEach(file -> filePathSet.add(file.getSourceFilePath()));
                    if (CollectionUtils.isNotEmpty(mdFileSet)) {
                        mdFileSet.forEach(mdFile -> {
                            MarkDownFile markDownFile = readMdFileByPath(mdFile, category);
                            sum.getAndIncrement();
                            // 如果已有文件，update
                            if (filePathSet.contains(mdFile)) {
                                mdFileService.updateBySourceFilePath(markDownFile);
                                log.info("update " + markDownFile.getSourceFilePath() + " success!");
                            } else {
                                // 否则，视为新文件，进行插入
                                mdFileService.insert(markDownFile);
                                log.info("insert " + markDownFile.getSourceFilePath() + " success!");
                            }
                        });
                    }
                } else {
                    throw new AppException(ErrorCodes.UPDATE_ERROR);
                }
            }
            operlog.addLog(null, null, new Date(), null, category, "update project " + url,
                    RESULT_SUCCESS, null);
            return sum.get();
        } catch (Exception e) {
            log.error(null, e);
            operlog.addLog(null, null, new Date(), null, category, "update project " + url,
                    RESULT_FAILURE, e.getMessage());
            return -1;
        }

    }


}
