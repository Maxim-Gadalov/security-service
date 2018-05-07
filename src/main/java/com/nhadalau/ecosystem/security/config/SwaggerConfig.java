package com.nhadalau.ecosystem.security.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {

    @Value("${api.swagger.info.title}")
    private String title;

    @Value("${api.swagger.info.description}")
    private String description;

    @Value("${api.swagger.info.version}")
    private String version;

    @Value("${api.swagger.info.terms-of-service-url}")
    private String termsOfServiceUrl;

    @Value("${api.swagger.info.license}")
    private String license;

    @Value("${api.swagger.info.license-url}")
    private String licenseUrl;

    @Bean
    public Docket apiDoc(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .paths(Predicates.not(PathSelectors.regex("/authenticated")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){

        Contact contact = null;

        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
                contact,
                license,
                licenseUrl);

        return apiInfo;
    }
}
