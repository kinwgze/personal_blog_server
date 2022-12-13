package zeee.blog.common.exception;

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

    /** 读取文件失败 */
    public static final int FILE_READ_ERROR = 21;

    /** 新建文件失败 */
    public static final int CREATE_FILE_ERROR = 21;

    /** 文件写入失败 */
    public static final int FILE_WRITE_ERROR = 22;

    /** 更新失败 */
    public static final int UPDATE_ERROR = 23;

    /** git fetch失败 */
    public static final int GIT_FETCH_ERROR = 24;

    /** 执行命令超时 */
    public static final int TIME_OUT = 25;

    /** sm4加密错误 */
    public static final int SM4_ENCRYPT_ERROR = 30;

    /** 时间处理错误 */
    public static final int CONVERT_TIME_ERROR = 40;

    // ===================访客系统错误码==================
    /** 申请时间错误 */
    public static final int REQUEST_TIME_ERROR = 101;

    /** 处理信息错误 */
    public static final int ADD_GUEST_VISIT_INFO_ERRRO = 102;

    /** 处理访客信息错误 */
    public static final int GUEST_INFO_ERROR = 103;

    /** 不在准许时间内，禁止进入 */
    public static final int NOT_IN_PERMIT_TIME = 104;

    /** 信息有误，禁止进入 */
    public static final int VALIDATION_FALSE = 105;


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
