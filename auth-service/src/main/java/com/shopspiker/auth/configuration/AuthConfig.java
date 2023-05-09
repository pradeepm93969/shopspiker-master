package com.shopspiker.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@Data
@PropertySource("classpath:auth-config.properties")
public class AuthConfig {

    @Value("${maxInvalidLoginAttemptsFromIP}")
    private int maxInvalidLoginAttemptsFromIP;

    @Value("${otpMaxRetries}")
    private int otpMaxRetries;

    @Value("${otpValidityInMin}")
    private int otpValidityInMin;

}
