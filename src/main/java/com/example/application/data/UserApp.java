package com.example.application.data;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "app_user",schema = "app")
@Data
public class UserApp {

    @Id
    @NotBlank(message = "tidak boleh kosong")
    @NotEmpty
    private String username;
    
    private String description;

    @Enumerated(EnumType.STRING)
    @JoinTable(name = "appuser_role",schema = "app")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
