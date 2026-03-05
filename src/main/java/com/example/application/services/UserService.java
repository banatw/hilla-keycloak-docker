package com.example.application.services;

import com.vaadin.hilla.BrowserCallable;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed({"USER"})
public class UserService {

    public String tesAdmin() {
        return "USER";
    }
    
}
