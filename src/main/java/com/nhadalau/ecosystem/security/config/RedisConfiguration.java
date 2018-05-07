package com.nhadalau.ecosystem.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConfiguration {

    private JedisConnectionFactory connectionFactory;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.server.hostname}") String redisHostName,
                                                         @Value("${redis.server.port}") Integer redisPort){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHostName);
        jedisConnectionFactory.setPort(redisPort);

        this.connectionFactory = jedisConnectionFactory;

        return jedisConnectionFactory;
    }

    @Bean
    @DependsOn("jedisConnectionFactory")
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
