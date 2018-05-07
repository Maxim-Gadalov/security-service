package com.nhadalau.ecosystem.security.business.verify;

import com.nhadalau.ecosystem.security.business.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenVerifyService {

    private static final Logger LOG = LoggerFactory.getLogger(TokenVerifyService.class);

    private RedisService redisService;

    @Autowired
    public TokenVerifyService(RedisService redisService) {
        this.redisService = redisService;
    }

    public boolean isTokenValid(String uidKey){
        boolean isVerified;
        String userToken = redisService.getUserTokenFromCache(uidKey);

        if(StringUtils.hasText(userToken)){
            // some jwt verification code

            isVerified = true;
        } else {
            isVerified = false;
        }

        return isVerified;
    }
}
