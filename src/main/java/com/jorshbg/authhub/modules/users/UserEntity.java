package com.jorshbg.authhub.modules.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorshbg.authhub.modules.logs.LogEntity;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Size(min = 2, max = 50)
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String userName;

    @NotBlank
    private String photoUrl;

    @Column(unique = true)
    @Email
    @NotBlank
    @NotNull
    private String email;

    @Column(unique = true)
    @Size(min = 10, max = 20)
    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(max = 80)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LogEntity> logs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> roles;
}
