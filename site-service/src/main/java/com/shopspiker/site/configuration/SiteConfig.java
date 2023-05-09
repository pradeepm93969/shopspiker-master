package com.shopspiker.site.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Locale;

@Configuration
@Data
@PropertySource("classpath:site-config.properties")
public class SiteConfig {

    @Value("${customLocales}")
    public List<String> customLocales;

}
