package com.nhadalau.ecosystem.security.business.verify;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/verify")
@Api(tags = "Verify token service", description = "Verify Token Endpoints")
public class TokenVerifyController {

    private static final Logger LOG = LoggerFactory.getLogger(TokenVerifyController.class);

    private TokenVerifyService tokenVerifyService;

    @Autowired
    public TokenVerifyController(TokenVerifyService tokenVerifyService) {
        this.tokenVerifyService = tokenVerifyService;
    }

    @GetMapping
    public ResponseEntity<Void> verifyToken(@RequestHeader("Authorization") String uidKey){
        boolean isVerified = tokenVerifyService.isTokenValid(uidKey);

        return new ResponseEntity<>(isVerified == Boolean.TRUE ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }
}
