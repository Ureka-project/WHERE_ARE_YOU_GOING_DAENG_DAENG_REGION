package com.daengdaeng_eodiga.project.Global.Redis.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisTokenRepository {
    public final RedisTemplate<String, String> redisTemplate;


    @Autowired
    public RedisTokenRepository(@Qualifier(value = "redisTemplate")RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String refreshToken, long expiration,String email) {
            if (expiration <= 0) {
                throw new IllegalArgumentException("유효하지 않은 만료 시간: " + expiration);
            }
            redisTemplate.opsForValue().set("refreshToken:" +refreshToken, email, expiration, TimeUnit.MILLISECONDS);
            String storedToken =  redisTemplate.opsForValue().get("refreshToken:" + refreshToken);
    }

    public String getToken(String email) {
        return redisTemplate.opsForValue().get("refreshToken:" + email);
    }

    public void deleteToken(String email) {
        redisTemplate.delete("refreshToken:" + email);
    }

    public void addToBlacklist(String token, long expiration, String email) {
        redisTemplate.opsForValue().set("blacklist:" + token, email, expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
}