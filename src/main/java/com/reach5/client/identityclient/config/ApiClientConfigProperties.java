package com.reach5.client.identityclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api.client")
public class ApiClientConfigProperties {

    Reach5 reach5;

    @Data
    public static class Reach5 {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private Integer timeout;
        private String tokenUrl;
    }

}

