package com.example.application.services;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.example.application.data.KeycloakUserRecord;
import com.vaadin.hilla.BrowserCallable;

import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed("ADMIN")
public class UserKeycloakService {
    private final Keycloak keycloak;


    public UserKeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @NonNull
    public List<UserRepresentation> getKeycloakUsers(@Nonnull String search) {
        return keycloak.realm("vaadin").users().search(search, 0, 10);
    }

    @NonNull
    public String getKeycloakUsername(@Nonnull String search) throws Exception {
        if(search.equals("")) {
            throw new Exception("search");
        }
        UserRepresentation userRepresentation =  keycloak.realm("vaadin").users().searchByUsername(search,true).get(0);
        return userRepresentation.getUsername();
    }

    @NonNull
    public List<KeycloakUserRecord> getKeycloakUserRecords() throws Exception {
        List<KeycloakUserRecord> keycloakUserRecords = new ArrayList<>(); 
        keycloak.realm("vaadin").users().search("").stream().forEach(data -> {
            KeycloakUserRecord keycloakUserRecord = new KeycloakUserRecord(data.getUsername(),data.getUsername());
            keycloakUserRecords.add(keycloakUserRecord);
        });
        return keycloakUserRecords;
    }
   
}
