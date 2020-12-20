package com.nobody.lua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(
            org.springframework.data.redis.connection.RedisConnectionFactory connFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFactory);
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(
            org.springframework.data.redis.connection.RedisConnectionFactory connFactory) {
        return new StringRedisTemplate(connFactory);
    }

}
