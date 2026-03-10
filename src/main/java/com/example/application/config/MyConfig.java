package com.example.application.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    // @Value("${keycloak.server-url}")
    // private String serverUrl;

    // @Value("${keycloak.realm}")
    // private String realm;

    // @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    // private String clientId;

    // @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    // private String clientSecret;
    
   
    @Bean
    public Keycloak getKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                            .serverUrl("http://localhost:7080")
                            .realm("vaadin")
                            .clientId("hilla")
                            .clientSecret("Ls97eKusCTK7uYdIbq1rvicg0LdQl2J3")
                            .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
        return keycloak;
    }

}
