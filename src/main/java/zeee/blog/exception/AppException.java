package zeee.blog.exception;

import org.apache.tomcat.util.res.StringManager;

/**
 * @author ：wz
 * @date ：Created in 2022/5/12 19:07
 * @description：异常业务封装类
 */
public class AppException extends RuntimeException {

    private static StringManager sm = StringManager.getManager(AppException.class);

    /**
     * 错误码
     */
    private int errorCode = 0;

    /**
     * 错误数据，用于生成完成的错误信息
     */
    private Object[] data = null;

    /**
     * 错误数据
     */
    private Object errorData;

    /**
     * 使用错误码构造异常应用对象
     * @param errorCode 错误码
     */
    public AppException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * 使用错误码和错误信息构造应用异常对象
     * @param errorCode 错误码
     * @param message   封装异常信息
     */
    public AppException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 使用错误码和封装异常信息构造应用异常对象
     * @param errorCode 错误码
     * @param cause     封装异常信息
     */
    public AppException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 用错误码、错误信息和封装异常信息构造应用异常对象
     * @param errorCode 错误码
     * @param message   错误信息
     * @param cause     封装异常信息
     */
    public AppException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 使用错误码和错误数据构造应用异常对象
     * @param errorCode 错误码
     * @param data      错误数据
     */
    public AppException(int errorCode, Object... data) {
        super();
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * 使用错误码、错误信息和错误数据构造应用异常对象
     * @param errorCode 错误码
     * @param message   错误信息
     * @param data      错误数据
     */
    public AppException(int errorCode, String message, Object... data) {
        super(message);
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * 用错误码、封装异常信息和错误数据构造应用异常对象
     * @param errorCode 错误码
     * @param cause     封装异常信息
     * @param data      错误数据
     */
    public AppException(int errorCode, Throwable cause, Object... data) {
        super(cause);
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * 用错误码、错误信息、封装异常信息和错误数据构造应用异常对象
     * @param errorCode 错误码
     * @param message   错误信息
     * @param cause     封装异常信息
     * @param data      错误数据
     */
    public AppException(int errorCode, String message, Throwable cause, Object... data) {
        super(message, cause);
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * 返回错误码
     * @return 错误码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 返回错误信息
     * @return 错误信息
     */
    public String getErrorMessage() {
        String msg = super.getMessage();
        String errorMsg = getErrorMessage(data);
        if (msg != null && errorMsg.startsWith("?")) {
            return msg;
        } else {
            return errorMsg;
        }
    }

    /**
     * 返回错误信息
     * @param data 错误信息中的替换数据
     * @return     对应的错误信息
     */
    public String getErrorMessage(Object... data) {
        return getErrorMessage(errorCode, data);
    }

    /**
     * 返回错误码对应的错误信息
     * @param errorCode 错误码
     * @return          对应的错误信息
     */
    public static String getErrorMessage(int errorCode) {
        return getErrorMessage(errorCode, (Object[]) null);
    }

    /**
     * 返回错误码对应的错误信息
     * @param errorCode 错误码
     * @param data      错误信息中的替换数据
     * @return          对应的错误信息
     */
    public static String getErrorMessage(int errorCode, Object... data) {
        if (data != null) {
            return sm.getString("errorCode." + errorCode, data);
        } else {
            return sm.getString("errorCode." + errorCode);
        }
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        String errMsg = "[" + errorCode + " - " + getErrorMessage() + "]";
        return msg == null ? errMsg : errMsg + " " + msg;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    /**
     * 返回不带错误码的异常信息
     * @return 异常信息
     */
    public String getMessageWithoutCode() {
        return super.getMessage();
    }

    public void setData(Object... data) {
        this.data = data;
    }
}
