package zeee.blog.common.loghttp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zeee.blog.utils.JsonUtil;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author wz
 * @date 2022/8/22
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(zeee.blog.common.loghttp.LogHttp)")
    public Object logHttp(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            LogHttpData data = new LogHttpData();
            data.setStartTime(System.currentTimeMillis());

            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (sra == null) {
                return joinPoint.proceed();
            }

            HttpServletRequest request = sra.getRequest();
            data.setId(UUID.randomUUID().toString());
            data.setUrl(request.getRequestURI());
            data.setMethod(request.getMethod());
//            String auth = request.getHeader("Authorization");
//            String ip = getIpAddress(request);
            Object[] args = joinPoint.getArgs();
            String params = "";
            try {
                if (null != args) {
                    List<Object> argList = new LinkedList<>();
                    for (Object arg : args) {
                        if (arg instanceof ServletResponse) {
                            continue;
                        }
                        argList.add(arg);
                    }
                    params = JsonUtil.objectToJsonString(argList.toArray());
                }
            } catch (Exception e) {
                log.error("failed to handler args");
            }

//            String reqLog = String.format("request[%s]: url: %s , method: %s , ip: %s , auth: %s , parameters: %s%n",
//                    data.getId(), data.getUrl(), data.getMethod(), ip, auth, params);


            String reqLog = String.format("request[%s]: url: %s , method: %s , parameters: %s%n",
                    data.getId(), data.getUrl(), data.getMethod(), params);
            log.info(reqLog);
            request.setAttribute(LogHttpData.NAME, data);

            Object result = joinPoint.proceed();
            HttpServletResponse response = sra.getResponse();
            String resp = "";
            if (result != null) {
                resp = JsonUtil.objectToJsonString(result);
            }
            String respLog = String.format("response[%s]: url: %s , method: %s , status: %d , use: %d ms",
                    data.getId(), data.getUrl(), data.getMethod(), response == null ? -1 : response.getStatus(),
                    System.currentTimeMillis() - data.getStartTime());
            log.info(respLog);
            return result;
        } catch (Throwable te) {
            log.error("logHttp", te);
            throw te;
        }
    }
}
