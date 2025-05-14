package io.github.ice_lfernandes.spring.log.utils.features.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enable automated logging of method execution details.
 * <p>
 * When applied to a method, this annotation triggers logging of:
 * <ul>
 *   <li>Method entry (with parameters, if enabled)</li>
 *   <li>Method exit (with return value, if enabled)</li>
 * </ul>
 *
 * @see org.slf4j.Logger
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {
    /**
     * Determines whether to log the method's return value
     * @return {@code true} to log return value (default), {@code false} to suppress
     */
    boolean logReturn() default true;

    /**
     * Determines whether to log method parameters
     * @return {@code true} to log parameters (default), {@code false} to suppress
     */
    boolean logParameters() default true;
}
