package zeee.blog.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zeee.blog.common.rpc.StateResult;

/**
 * @author wz
 * @date 2022/8/31
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AppException.class)
    public StateResult handleException(AppException ae) {
        StateResult result = new StateResult();
        result.setState(StateResult.FAILURE);
        result.setErrorCode(ae.getErrorCode());
        result.setFailureMessage(ae.getErrorMessage());
        return result;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public StateResult handleException(Exception e) {
        log.error(null, e);
        StateResult result = new StateResult();
        result.setState(StateResult.FAILURE);
        result.setErrorCode(ErrorCodes.UNKNOWN_ERROR);
        result.setFailureMessage(ErrorCodes.getErrorMessage(ErrorCodes.UNKNOWN_ERROR));
        return result;
    }
}
