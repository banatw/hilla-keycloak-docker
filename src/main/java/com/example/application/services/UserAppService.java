package com.example.application.services;

import java.util.Optional;

import org.jspecify.annotations.NonNull;

import com.example.application.data.UserApp;
import com.example.application.data.UserAppRepository;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.ListRepositoryService;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed("ADMIN")
public class UserAppService extends ListRepositoryService<UserApp,String,UserAppRepository> {
    private UserAppRepository userAppRepository;

    public UserAppService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public UserApp addNew() {
        return new UserApp();
    }

    public UserApp save(UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    public UserApp findbyUsername(@NonNull Optional<String> username) {
        return userAppRepository.findByUsername(username.get());
    }

    public UserApp delete(UserApp userApp) {
        userAppRepository.delete(userApp);
        return userApp;
    }
}
