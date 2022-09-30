package com.recommit.assignment.formula1.formula1dataservice.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * This Class is used to create an Instance of application.yml application properties. It will load all values when
 * application will start. This way we can use our required properties key value using getter method
 */
@Data
@Validated
@Component
@ConfigurationProperties(prefix = "service.param")
public class ServiceProperties {

    private JwtConfig jwtConfig;

    @Data
    public static class JwtConfig {
        private String header;
        private String prefix;
        private String secret;
        private long expiration;
    }
}
