package com.csyd.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @author zmh
 * @date 2018/2/1813:42
 * Spring Data Redis 的基本款
 * 只要Gradle或Maven引入依赖 就可以如此使用Redis 作为缓存
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
