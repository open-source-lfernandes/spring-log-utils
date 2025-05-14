package io.github.ice_lfernandes.spring.log.utils.features.annotations;

import io.github.ice_lfernandes.spring.log.utils.features.enums.MaskedType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated field's sensitive data should be masked during logging operations.
 *
 * <p>
 * The masking behavior can be controlled either by a predefined {@link MaskedType} or a custom regular expression.
 * <p>
 * <b>If a {@link #customMaskRegex()} is specified and non-empty, it takes precedence over the regex derived from {@link #maskedType()}.</b>
 * <p>
 * This allows fine-grained control over masking patterns for specific fields.
 *
 * @see MaskedType
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskSensitiveData {
    /**
     * Specifies a predefined masking pattern type.
     *
     * <p>
     * This value is used as the default masking strategy <i>only if no custom regex is provided via {@link #customMaskRegex()}.</i>
     *
     * @return The predefined masking type. Defaults to {@link MaskedType#ALL}.
     */
    MaskedType maskedType() default MaskedType.ALL;

    /**
     * Defines a custom regular expression to mask sensitive data.
     *
     * <p>
     * <b>This property has higher priority than {@link #maskedType()}.</b> If provided and non-blank,
     * <p>
     * the custom regex will override the regex associated with {@link #maskedType()}. Use this to define
     * <p>
     * field-specific masking rules that deviate from the predefined patterns.
     *
     * @return A custom regex pattern for masking. Defaults to an empty string (no custom regex).
     */
    String customMaskRegex() default "";
}
