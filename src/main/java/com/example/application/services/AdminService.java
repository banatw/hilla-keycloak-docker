package com.example.application.services;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

import com.vaadin.hilla.BrowserCallable;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed({"ADMIN"})
public class AdminService {
    
   


    @NonNull
    public String tesAdmin() {
        return "ADMIN";
    }
    
   
}
