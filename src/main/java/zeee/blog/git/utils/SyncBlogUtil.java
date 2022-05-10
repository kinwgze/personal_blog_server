package zeee.blog.git.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 19:35
 * @description：
 */
@Service("SyncBlogUtil")
public class SyncBlogUtil {

    public static final String BIN_SH_C = "/bin/sh -c ";

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(SyncBlogUtil.class);

    /**
     * 从给定的git url clone项目到本地
     * @param url
     * @return
     */
    public int gitCloneFromGithub(String url) {
        String command = BIN_SH_C + "cd /var/git && git clone ";

//        //在有指定环境和工作目录的独立进程中执行指定的字符串命令
//        Process exec (String command, String[]envp, File dir)
        int exitValue = -9999;
        BufferedReader reader = null;
        try {
            log.info(command + url);
//            Process process = Runtime.getRuntime().exec(command + url);
            Process process = Runtime.getRuntime().exec(BIN_SH_C + "/var/git/syncgit.sh");
            exitValue = process.waitFor();
            if (0 != exitValue) {
                log.error("Exec shell failed. Error code is : " + exitValue);
            }
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                log.error("ErrorMsg=" + line);
            }

        } catch (Exception e) {
            log.error(null, e);
        }
        return exitValue;

    }

}
