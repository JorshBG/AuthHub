package com.jorshbg.authhub.modules.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorshbg.authhub.modules.logs.LogEntity;
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

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String userName;

    @NotBlank
    private String photoUrl;

    @Email
    @NotBlank
    private String email;

    @Size(min = 10, max = 20)
    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(max = 80)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL)
    private List<LogEntity> logs;
}
