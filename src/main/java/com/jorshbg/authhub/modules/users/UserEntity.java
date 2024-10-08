package com.jorshbg.authhub.modules.users;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorshbg.authhub.modules.logs.LogEntity;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private String username;

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

    @JsonIgnore
    @NotBlank
    @Size(max = 80)
    private String password;

    @NotNull
    private Boolean status = true;

    @JsonIgnore
    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LogEntity> logs;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> roles;

    @JsonProperty("roles")
    public List<String> getArrayRoles(){
        return this.roles.stream()
                .map(role -> role.getRole().getName())
                .collect(Collectors.toList());
    }
}
