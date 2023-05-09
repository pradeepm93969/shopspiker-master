package com.shopspiker.auth.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

    @Cacheable(value = "LoginAttempt", key = "#key")
    public int getById(String key) {
        return 0;
    }

    @CachePut(value = "LoginAttempt", key = "#key")
    public int update(String key, int attempts) {
        return attempts;
    }

    @CacheEvict(value = "LoginAttempt", key = "#key")
    public void delete(String key) {

    }
}
