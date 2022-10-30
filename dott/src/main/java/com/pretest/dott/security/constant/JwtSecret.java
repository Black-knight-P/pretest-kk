package com.pretest.dott.security.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

public class JwtSecret {
    private static String secretKey;

    protected static void setSecretKey(final String secretKey) {
        JwtSecret.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    @RequiredArgsConstructor
    @Configuration
    public static class JwtSecretConfig {
        @Value("${dott.security.jwt.secret}")
        private String secret;

        @Bean
        public InitializingBean eventsInitializer() {
            return () -> JwtSecret.setSecretKey(secret);
        }
    }
}
