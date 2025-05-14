package io.github.ice_lfernandes.spring.log.utils.features.interfaces;

import io.github.ice_lfernandes.spring.log.utils.features.enums.MaskedType;
import io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods;
import io.github.ice_lfernandes.spring.log.utils.features.annotations.MaskSensitiveData;
import lombok.SneakyThrows;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.Objects.nonNull;

/**
 * Interface defining a mechanism to mask sensitive data in object fields during logging.
 * <p>
 * This interface provides a default implementation to serialize objects into a string
 * representation while applying masking to fields annotated with {@link MaskSensitiveData}.
 * The masking behavior is determined by regex patterns defined in {@link MaskedType} or
 * custom regex provided in the annotation.
 * </p>
 *
 * <p>The following classes are eligible for masking by default:
 * <ul>
 *   <li>{@link String}</li>
 *   <li>{@link java.math.BigDecimal}</li>
 *   <li>{@link java.time.LocalDate}</li>
 *   <li>{@link java.time.LocalDateTime}</li>
 *   <li>{@link Boolean}</li>
 *   <li>{@link Integer}</li>
 *   <li>{@link Float}</li>
 *   <li>{@link Short}</li>
 *   <li>{@link Double}</li>
 * </ul>
 * </p>
 *
 * @see MaskSensitiveData
 * @see MaskedType
 */
public interface LogMask extends Serializable {

    /**
     * Set of classes whose fields are eligible for masking.
     */
    Set<Class<?>> classesMaskEnabled = Set.of(
            String.class,
            BigDecimal.class,
            LocalDate.class,
            LocalDateTime.class,
            Boolean.class,
            Integer.class,
            Float.class,
            Short.class,
            Double.class
    );

    /**
     * Generates a masked string representation of an object. Fields annotated with
     * {@link MaskSensitiveData} are masked using regex patterns from {@link MaskedType}
     * or custom regex defined in the annotation.
     *
     * @param object The object to serialize and mask
     * @return A string in the format {@code ClassName{field1=value1, field2=***masked***}}
     * @throws RuntimeException Wraps reflection-related exceptions via {@link SneakyThrows}
     *
     * <p>Example output:
     * <pre>{@code User{name=***, email=****@***.com}}</pre>
     * </p>
     */
    @SneakyThrows
    default String mask(Object object) {
        Class<?> clazz = object.getClass();

        StringJoiner sj = new StringJoiner(", ", clazz.getSimpleName() + "{", "}");
        for (Field field : clazz.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            Object value = field.get(object);

            if (shouldBeMasked(field, value)) {
                MaskSensitiveData ann = Objects.requireNonNull(field.getAnnotation(MaskSensitiveData.class),
                        "Field should be annotated with @MaskSensitiveData");
                var regex = chooseRegexToMask(ann);
                value = LoggingCommonsMethods.mask(value.toString(), regex);
            }
            sj.add(field.getName() + "=" + value);
        }
        return sj.toString();
    }

    /**
     * Determines the appropriate regex pattern for masking a field. Prioritizes
     * {@link MaskSensitiveData#customMaskRegex()} if provided and non-blank; otherwise,
     * uses the regex from {@link MaskSensitiveData#maskedType()}.
     *
     * @param maskSensitiveData The annotation instance on the field
     * @return The regex pattern to use for masking
     */
    private static String chooseRegexToMask(MaskSensitiveData maskSensitiveData) {
        if (nonNull(maskSensitiveData.customMaskRegex()) && !maskSensitiveData.customMaskRegex().isBlank())
            return maskSensitiveData.customMaskRegex();
        return maskSensitiveData.maskedType().getRegex();
    }

    /**
     * Determines whether a field should be masked based on annotation presence and value type eligibility.
     *
     * <p>A field is considered for masking if:
     * <ul>
     *   <li>It is annotated with {@link MaskSensitiveData}</li>
     *   <li>The value's type is either a primitive type (e.g., int, boolean) or included in the {@link #classesMaskEnabled} set</li>
     * </ul>
     * </p>
     *
     * @param field The field to check for masking eligibility
     * @param value The value of the field to inspect its type
     * @return {@code true} if the field requires masking, {@code false} otherwise
     *
     * @see MaskSensitiveData
     * @see #classesMaskEnabled
     */
    private static boolean shouldBeMasked(Field field, Object value) {
        return field.isAnnotationPresent(MaskSensitiveData.class)
                && (value.getClass().isPrimitive() || classesMaskEnabled.contains(value.getClass()));
    }
}
