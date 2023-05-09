package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.shopspiker.*.jpa.entity")
@EnableJpaRepositories(basePackages = "com.shopspiker.*.jpa.repository")
@ComponentScan("com.shopspiker.*")
public class ShopSpikerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSpikerApplication.class, args);
    }

}
