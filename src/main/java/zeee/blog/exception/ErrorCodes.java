package zeee.blog.exception;

import org.apache.tomcat.util.res.StringManager;

/**
 * @author ：wz
 * @date ：Created in 2022/4/7 19:15
 * @description：errorCodes
 */
public class ErrorCodes {

    private static StringManager sm = StringManager.getManager(ErrorCodes.class);

    public static final int SUCCESS = 0;

    public static final int UNKNOWN_ERROR = 11;

    /**
     * 读取文件失败
     */
    public static final int FILE_READ_ERROR = 21;

    /**
     * 新建文件失败
     */
    public static final int CREATE_FILE_ERROR = 21;

    /**
     * 文件写入失败
     */
    public static final int FILE_WRITE_ERROR = 21;

    /**
     * 根据错误码，返回对应的错误信息
     * @param errorCode 错误码
     * @return 对应的错误信息提示
     */
    public static String getErrorMessage(final int errorCode) {
        return sm.getString("errorCode." + errorCode);
    }

    public static String getErrorMessage(final int errorCode, Object... args) {
        return sm.getString("errorCode." + errorCode, args);
    }
}
