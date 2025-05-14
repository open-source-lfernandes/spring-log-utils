package io.github.ice_lfernandes.spring.log.utils.features.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for logging execution details of HTTP-related methods.
 * <p>
 * When applied to controller methods or HTTP endpoints, this annotation enables automatic logging of:
 * <ul>
 *   <li>HTTP method invocation with parameters (when enabled)</li>
 *   <li>Method execution results (when enabled)</li>
 *   <li>Request/response details</li>
 * </ul>
 *
 * @see org.slf4j.Logger
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethodLogExecution {
    /**
     * Controls whether to log the method's return value (typically HTTP response)
     * @return {@code true} to log return values (default), {@code false} to disable response logging
     */
    boolean logReturn() default true;
    /**
     * Controls whether to log method parameters (typically HTTP request parameters)
     * @return {@code true} to log input parameters (default), {@code false} to disable request logging
     */
    boolean logParameters() default true;
}
