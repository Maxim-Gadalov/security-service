package com.nhadalau.ecosystem.security.business.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);

    public static final String REDIS_HASH_NAME = "userToken";

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setUserTokenToCache(String uidKey, String jwt){
        redisTemplate.opsForHash().put(REDIS_HASH_NAME, uidKey, jwt);

        LOG.debug("User token was successfully stored in Redis");
    }

    public void removeFromCache(String uidKey){
        redisTemplate.opsForHash().delete(REDIS_HASH_NAME, uidKey);

        LOG.debug("User token was successfully removed from Redis");
    }

    public String getUserTokenFromCache(String uidKey){
        String userToken = (String) redisTemplate.opsForHash().get(REDIS_HASH_NAME, uidKey);

        LOG.debug("User token was successfully got from Redis");

        return userToken;
    }

    public boolean isPresentInCache(String uidKey){
        boolean result = redisTemplate.opsForHash().hasKey(REDIS_HASH_NAME, uidKey);

        return result;
    }
}
