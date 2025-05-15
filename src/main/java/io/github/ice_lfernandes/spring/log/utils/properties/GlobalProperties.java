package io.github.ice_lfernandes.spring.log.utils.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.log-utils.global")
public record GlobalProperties(
        HttpMethodProperties httpMethod
) {
}
