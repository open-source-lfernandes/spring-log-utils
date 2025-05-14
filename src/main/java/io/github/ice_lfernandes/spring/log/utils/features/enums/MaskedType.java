package io.github.ice_lfernandes.spring.log.utils.features.enums;

import lombok.Getter;

/**
 * Enum representing different types of data masking strategies with associated regex patterns.
 * Each constant defines a regex pattern to identify sensitive data fields that require masking.
 *
 * <p>Example usage:
 * <pre>
 * MaskedType.EMAIL.getRegex(); // Returns regex for email masking
 * </pre>
 * </p>
 */
@Getter
public enum MaskedType {
    /**
     * Masks all fields (catch-all regex).
     * Regex: {@code \\S}
     */
    ALL("\\S"),
    /**
     * Masks email addresses.
     * Regex: {@code .(?=.{4})(?=[^@])(?=[^@]{4}).}
     */
    EMAIL(".(?=.{4})(?=[^@])(?=[^@]{4})."),
    /**
     * Masks document numbers.
     * Regex: {@code .(?=.{3})}
     */
    DOCUMENT(".(?=.{3})"),
    /**
     * Masks names.
     * Regex: {@code .(?=[^ ])(?=[^ ]{2}).}
     */
    NAME(".(?=[^ ])(?=[^ ]{2})."),
    /**
     * Masks dates.
     * Regex: {@code .(?=[^ \\/.-].{3}).}
     */
    DATE(".(?=[^ \\\\/.-].{3})."),
    /**
     * Masks addresses.
     * Regex: {@code .(?=.{3})[^, ]}
     */
    ADDRESS(".(?=.{3})[^, ]"),
    /**
     * Masks zip codes.
     * Regex: {@code .(?=.{3})[^-]}
     */
    ZIP_CODE(".(?=.{3})[^-]"),
    /**
     * Masks numeric values.
     * Regex: {@code \d}
     */
    NUMBER("\\d"),
    /**
     * Masks telephone numbers.
     * Regex: {@code .(?=.{2})[^-]}
     */
    TELEPHONE(".(?=.{2})[^-]");

    private final String regex;

    MaskedType(String regex) {
        this.regex = regex;
    }
}
