package com.linebot.config;

import com.linebot.model.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, UserStatus> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserStatus> redisTemplate = new RedisTemplate<String, UserStatus>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer("UserStatus"));
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
        redisTemplate.setHashValueSerializer(redisTemplate.getValueSerializer());
        return redisTemplate;
    }
}
