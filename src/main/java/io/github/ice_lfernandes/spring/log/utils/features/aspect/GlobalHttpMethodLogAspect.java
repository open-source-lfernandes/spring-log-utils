package io.github.ice_lfernandes.spring.log.utils.features.aspect;

import io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods;
import io.github.ice_lfernandes.spring.log.utils.properties.GlobalProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 * Aspect for logging execution of HTTP methods based on configuration properties.
 * This aspect intercepts methods annotated with Spring's HTTP mapping annotations (e.g., PostMapping, PutMapping)
 * and logs their execution if enabled via configuration.
 *
 * <p>The aspect is conditionally enabled based on the property {@code spring.loggable-utils.global.httpMethod.enabled}.
 * If the property is not set, it is enabled by default.</p>
 */
@Aspect
@Component
@EnableConfigurationProperties(GlobalProperties.class)
@ConditionalOnProperty(
        prefix = "spring.log-utils.global.httpMethod",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class GlobalHttpMethodLogAspect {

    private final GlobalProperties globalProperties;

    /**
     * Around advice that intercepts HTTP method executions annotated with Spring's mapping annotations.
     * Logs method execution details if logging is enabled via configuration.
     *
     * @param joinPoint The proceeding join point representing the intercepted method
     * @return The result of the intercepted method execution
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    @SneakyThrows
    public Object logExecutionHttpMethod(ProceedingJoinPoint joinPoint) {
        if (isLoggingEnabled())
            return LoggingCommonsMethods.logInterceptJoinPoint(joinPoint);
        return joinPoint.proceed();
    }

    private boolean isLoggingEnabled() {
        return isNull(globalProperties.httpMethod()) || Boolean.TRUE.equals(globalProperties.httpMethod().enabled());
    }
}