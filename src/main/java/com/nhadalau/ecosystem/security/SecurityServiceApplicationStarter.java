package com.nhadalau.ecosystem.security;

import com.nhadalau.ecosystem.security.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
@SpringBootApplication
@EnableEurekaClient
public class SecurityServiceApplicationStarter {

    public static void main(String...args){
        SpringApplication.run(SecurityServiceApplicationStarter.class, args);
    }
}
