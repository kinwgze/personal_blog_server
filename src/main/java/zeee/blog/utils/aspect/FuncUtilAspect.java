package zeee.blog.utils.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zeee.blog.utils.JsonUtil;

import java.util.UUID;

/**
 * @author wz
 * @date 2022/8/23
 */
@Aspect
@Component
public class FuncUtilAspect {

    private static final Logger log = LoggerFactory.getLogger(FuncUtilAspect.class);

    @Pointcut("execution(public * zeee.blog.utils.FuncUtil.runCommandThrowException(..))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            String logId = UUID.randomUUID().toString();
            // 获取参数名&参数值
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            String[] names = ms.getParameterNames();
            Object[] values = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            if (names != null && values != null) {
                for (int i = 0; i < values.length; i++) {
                    sb.append(names[i]).append(": ").append(JsonUtil.objectToJsonString(values[i])).append("\n");
                }
                log.info(String.format("RunCommand[%s]: %s", logId, sb));
            }
            Object result = joinPoint.proceed();
            log.info(String.format("RunCommand[%s]: Result: %s\n, time: %d", logId, JsonUtil.objectToJsonString(result),
                    System.currentTimeMillis() - startTime));
            return result;
        } catch (Throwable e) {
            log.error(null, e);
            throw e;
        }
    }
}
