package com.example.application.services;

import java.util.Optional;

import org.jspecify.annotations.NonNull;

import com.vaadin.hilla.BrowserCallable;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed({"ADMIN"})
public class AdminService {

    @NonNull
    public Optional<String> tesAdmin() {
        return Optional.of("ADMIN");
    }
    
}
