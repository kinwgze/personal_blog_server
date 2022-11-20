package zeee.blog.git.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.common.exception.AppException;
import zeee.blog.common.exception.ErrorCodes;
import zeee.blog.utils.commandutil.CommandUtil;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 19:35
 * @description：
 */
@Service("SyncBlogUtil")
public class SyncBlogUtil {

    @Resource
    private CommandUtil commandUtil;

//    public static final String BIN_SH_C = "/bin/sh -c ";

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(SyncBlogUtil.class);

    /**
     * 从给定的git url clone项目到本地
     * @param url git项目地址
     * @return 0代表成功
     */
    public int gitCloneFromGithub(String url) {
        String result = commandUtil.runCommandThrowException(new String[]{"/bin/sh", "-c", "git clone '" + url + "'"}, null, new File("/var/git"), 100 * 1000);
        if (StringUtils.isNotEmpty(result)) {
            String[] rsArray = StringUtils.split(result, "\n");
            if (rsArray != null && rsArray.length > 0) {
                int errorCode = Integer.parseInt(rsArray[rsArray.length - 1].trim());
                if (errorCode != 0) {
                    throw new AppException(ErrorCodes.UNKNOWN_ERROR);
                }
            }
        }
        return 0;
    }

}
