package com.nhadalau.ecosystem.security.business.authenticate;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/authenticate")
@Api(tags = "Authenticate service", description = "Authenticate Endpoints")
public class AuthenticateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateController.class);

    private AuthenticateService authenticateService;

    @Autowired
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @GetMapping
    public ResponseEntity<Void> authenticate() throws URISyntaxException {

        URI authenticateUrl = authenticateService.getAuthenticateURI();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(authenticateUrl);

        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);

    }
}
