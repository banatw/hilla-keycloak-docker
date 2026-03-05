package com.example.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class MyConfig {
    
    public ClientRegistrationRepository clientRegistrationRepository() {
        return null;
    }
}
