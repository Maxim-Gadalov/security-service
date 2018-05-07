package com.nhadalau.ecosystem.security.business.authenticated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhadalau.ecosystem.security.business.redis.RedisService;
import com.nhadalau.ecosystem.security.util.UidKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class AuthenticatedService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticatedService.class);

    private String grantType;
    private String clientId;
    private String clientSecret;
    private String redirectURI;
    private String tokenEndpoint;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private UidKeyGenerator uidGenerator;
    private RedisService redisService;

    public AuthenticatedService(@Value("${security.oauth2.client.grant-type}") String grantType,
                                @Value("${security.oauth2.client.client-id}") String clientId,
                                @Value("${security.oauth2.client.client-secret}") String clientSecret,
                                @Value("${security.oauth2.client.registered-redirect-uri}") String redirectURI,
                                @Value("${security.oauth2.client.access-token-uri}") String tokenEndpoint,
                                RestTemplate restTemplate, ObjectMapper objectMapper, UidKeyGenerator uidGenerator,
                                RedisService redisService) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
        this.tokenEndpoint = tokenEndpoint;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.uidGenerator = uidGenerator;
        this.redisService = redisService;
    }

    public String getToken(String code) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectURI);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenEndpoint, requestEntity, String.class);

        HttpStatus status = responseEntity.getStatusCode();

        if(status.is2xxSuccessful()) {
            String body = responseEntity.getBody();

            String userToken;
            try {
                userToken = objectMapper.readTree(body).get("id_token").asText();
            } catch (IOException exception) {
                throw new IllegalArgumentException(exception.getMessage());
            }

            String uidKey = uidGenerator.generateUID();

            redisService.setUserTokenToCache(uidKey, userToken);

            return uidKey;
        } else {
            throw new IllegalArgumentException("Invalid code");
        }
    }
}
