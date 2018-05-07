package com.nhadalau.ecosystem.security.business.logout;

import com.nhadalau.ecosystem.security.business.redis.RedisService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/logout")
@Api(tags = "Logout service", description = "Logout Endpoints")
public class LogoutController {

    private RedisService redisService;
    private String logoutEndpoint;

    @Autowired
    public LogoutController(RedisService redisService,
                         @Value("${security.oauth2.client.logout-uri}") String logoutEndpoint) {
        this.redisService = redisService;
        this.logoutEndpoint = logoutEndpoint;
    }

    @GetMapping
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String uidKey) throws URISyntaxException {

        if(StringUtils.hasText(uidKey)){
            boolean isInCache = redisService.isPresentInCache(uidKey);

            if(isInCache){
                redisService.removeFromCache(uidKey);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(new URI(logoutEndpoint));

                return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
