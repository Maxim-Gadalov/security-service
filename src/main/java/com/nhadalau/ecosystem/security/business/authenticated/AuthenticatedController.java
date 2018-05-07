package com.nhadalau.ecosystem.security.business.authenticated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/authenticated")
public class AuthenticatedController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticatedController.class);

    private AuthenticatedService authenticatedService;

    @Autowired
    public AuthenticatedController(AuthenticatedService authenticatedService) {
        this.authenticatedService = authenticatedService;
    }

    @GetMapping
    public ResponseEntity<String> getAuthToken(@RequestParam("code") String code){
        ResponseEntity<String> response;

        if(StringUtils.hasText(code)){
            String tokenId = authenticatedService.getToken(code);

            response = new ResponseEntity<>(tokenId, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
