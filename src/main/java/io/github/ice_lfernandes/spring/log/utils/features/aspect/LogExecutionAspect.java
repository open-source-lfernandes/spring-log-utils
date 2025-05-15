package io.github.ice_lfernandes.spring.log.utils.features.aspect;

import io.github.ice_lfernandes.spring.log.utils.features.annotations.LogExecution;
import io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * AspectJ component for intercepting and logging method executions annotated with {@link LogExecution}.
 * <p>
 * This aspect provides around advice to monitor method execution details including:
 * <ul>
 *   <li>Method entry/exit timing</li>
 *   <li>Input parameters (when enabled)</li>
 *   <li>Return values (when enabled)</li>
 * </ul>
 *
 * @version 1.0
 * @see org.aspectj.lang.annotation.Around
 */
@Aspect
@Component
public class LogExecutionAspect {

    /**
     * Around advice for methods annotated with {@link LogExecution}.
     * <p>
     * Intercepts method execution and delegates logging to {@link LoggingCommonsMethods}.
     *
     * @param joinPoint The proceeding join point for method interception
     * @return The result of the intercepted method execution
     * @see LoggingCommonsMethods#logInterceptJoinPoint
     */
    @Around("@annotation(io.github.ice_lfernandes.spring.log.utils.features.annotations.LogExecution)")
    @SneakyThrows
    public Object logExecution(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        var logExecution = Objects.requireNonNull(methodSignature.getMethod().getAnnotation(LogExecution.class),
                "LogExecution should not be null");
        return LoggingCommonsMethods.logInterceptJoinPoint(joinPoint, logExecution.logReturn(), logExecution.logParameters());
    }
}
