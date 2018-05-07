package com.nhadalau.ecosystem.security.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(10_000, TimeUnit.MILLISECONDS);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setConnectionManager(connectionManager);

        HttpClient httpClient = clientBuilder.build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setMessageConverters(Arrays.asList(
                new StringHttpMessageConverter(),
                new FormHttpMessageConverter(),
                new MappingJackson2HttpMessageConverter()
        ));

        return restTemplate;
    }
}
