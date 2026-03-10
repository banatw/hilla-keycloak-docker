package com.example.application.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.application.data.Role;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class RolesService {

    public Set<String> getRoles() {
        return Stream.of(Role.values()).map(Role::name).collect(Collectors.toSet());
    }
}
