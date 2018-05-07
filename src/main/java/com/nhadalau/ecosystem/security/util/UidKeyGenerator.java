package com.nhadalau.ecosystem.security.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UidKeyGenerator {

    public String generateUID(){

        String uidKey = UUID.randomUUID().toString();

        return uidKey;
    }
}
