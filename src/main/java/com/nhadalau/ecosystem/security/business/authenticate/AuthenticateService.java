package com.nhadalau.ecosystem.security.business.authenticate;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AuthenticateService {

    private String authenticateEndpoint;
    private String redirectURI;
    private String responseType;
    private String scope;
    private String clientId;

    @Autowired
    public AuthenticateService(@Value("${security.oauth2.client.user-authorization-uri}") String authenticateEndpoint,
                               @Value("${security.oauth2.client.registered-redirect-uri}") String redirectURI,
                               @Value("${security.oauth2.client.response-type}") String responseType,
                               @Value("${security.oauth2.client.scope}") String scope,
                               @Value("${security.oauth2.client.client-id}") String clientId) {
        this.authenticateEndpoint = authenticateEndpoint;
        this.redirectURI = redirectURI;
        this.responseType = responseType;
        this.scope = scope;
        this.clientId = clientId;
    }

    public URI getAuthenticateURI() throws URISyntaxException {
        URI authenticateURI = new URIBuilder(authenticateEndpoint)
                .setParameter("response_type", responseType)
                .setParameter("scope", scope)
                .setParameter("client_id", clientId)
                .setParameter("redirect_uri", redirectURI)
                .build();

        return authenticateURI;
    }
}
