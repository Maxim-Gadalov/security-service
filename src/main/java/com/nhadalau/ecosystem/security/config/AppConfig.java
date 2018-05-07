package com.nhadalau.ecosystem.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Import({
        RestTemplateConfig.class,
        RedisConfiguration.class,
        SwaggerConfig.class
})
@PropertySources(value = {
        @PropertySource("classpath:swagger.properties")
})
@EnableSwagger2
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
