package com.shopspiker.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<String, RedisCacheConfiguration>();

        //Site Service
        cacheConfigurations.put("TaxRate", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put("Site", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));

        //Auth Service
        cacheConfigurations.put("Role", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put("Permission", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put("Client", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));
        cacheConfigurations.put("LoginAttempt", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)));


        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

}
