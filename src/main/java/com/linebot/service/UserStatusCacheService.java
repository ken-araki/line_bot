package com.linebot.service;

import com.linebot.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class UserStatusCacheService {
    private RedisTemplate redisTemplate;

    public void set(String userId, UserStatus userStatus) {
        redisTemplate.opsForValue().set(userId, userStatus);
        redisTemplate.expire(userId, 30, TimeUnit.MINUTES);
    }

    @Nullable
    public UserStatus get(String userId) {
        if (redisTemplate.hasKey(userId)) {
            return (UserStatus) redisTemplate.opsForValue().get(userId);
        }
        return null;
    }

    public boolean delete(String userId) {
        return redisTemplate.delete(userId);
    }
}
