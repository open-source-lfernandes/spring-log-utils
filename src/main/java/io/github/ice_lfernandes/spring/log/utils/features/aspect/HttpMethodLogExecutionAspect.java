package io.github.ice_lfernandes.spring.log.utils.features.aspect;

import io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods;
import io.github.ice_lfernandes.spring.log.utils.features.annotations.HttpMethodLogExecution;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Aspect for intercepting and logging executions of methods annotated with {@link HttpMethodLogExecution}.
 * This aspect provides centralized logging behavior for HTTP method executions based on annotation configuration.
 *
 * <p>The advice wraps annotated methods to log execution details, including method return values and resource closure status
 * as specified in the {@link HttpMethodLogExecution} annotation.</p>
 *
 * @see org.aspectj.lang.annotation.Around
 * @see org.aspectj.lang.ProceedingJoinPoint
 */
@Aspect
@Component
public class HttpMethodLogExecutionAspect {

    /**
     * Around advice that intercepts method executions annotated with {@link HttpMethodLogExecution}.
     * Logs method execution details according to the annotation's configuration parameters.
     *
     * @param joinPoint The proceeding join point representing the intercepted method
     * @return The result of the intercepted method execution
     * @throws Throwable If the intercepted method throws an exception
     * @see HttpMethodLogExecution#logReturn()
     * @see HttpMethodLogExecution#logParameters()
     */
    @Around("@annotation(io.github.ice_lfernandes.spring.log.utils.features.annotations.HttpMethodLogExecution)")
    @SneakyThrows
    public Object logExecutionHttpMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        var httpMethodLogExecution = Objects.requireNonNull(
                methodSignature.getMethod().getAnnotation(HttpMethodLogExecution.class), "HttpMethodLogExecution should not be null");
        return LoggingCommonsMethods.logInterceptJoinPoint(joinPoint, httpMethodLogExecution.logReturn(), httpMethodLogExecution.logParameters());
    }
}
