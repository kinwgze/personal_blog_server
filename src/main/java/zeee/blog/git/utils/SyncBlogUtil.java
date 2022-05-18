package zeee.blog.git.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zeee.blog.exception.AppException;
import zeee.blog.exception.ErrorCodes;
import zeee.blog.utils.FuncUtil;

import java.io.File;

/**
 * @author ：wz
 * @date ：Created in 2022/5/6 19:35
 * @description：
 */
@Service("SyncBlogUtil")
public class SyncBlogUtil {

//    public static final String BIN_SH_C = "/bin/sh -c ";

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
        String result = FuncUtil.runCommandThrowException(new String[]{"/bin/sh", "-c", "git clone '" + url + "'"}, null, new File("/var/git"), 100 * 1000);
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
