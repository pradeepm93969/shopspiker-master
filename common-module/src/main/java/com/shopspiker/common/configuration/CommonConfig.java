package com.shopspiker.common.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@Data
@PropertySource("classpath:common-config.properties")
public class CommonConfig {

    @Value("${reqResLoggingEnabled}")
    public boolean reqResLoggingEnabled;

}
