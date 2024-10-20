package com.jorshbg.authhub.modules.users;

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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class to define the table in database system -> users
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Default random UUID to use as id in the table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * First name of the user.
     * this needs to have at least 2 characters and 50 characters as max.
     * It cannot be null, or have only white spaces
     */
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    /**
     * Last name of the user.
     * this needs to have at least 2 characters and 50 characters as max.
     * It cannot be null, or have only white spaces
     */
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    /**
     * Username for use it in the system.
     * this needs to have at least 2 characters and 50 characters as max.
     * It cannot be null, or have only white spaces
     * It cannot be duplicated
     */
    @Size(min = 2, max = 50)
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String username;

    /**
     * Photo url of the user
     * Max = 255 characters
     * It cannot be null, or have only white spaces
     */
    @NotBlank
    private String photoUrl;

    /**
     * Email of the user
     * It cannot be null, or have only white spaces
     * It cannot be duplicated
     * It needs to be in email format
     */
    @Column(unique = true)
    @Email
    @NotBlank
    @NotNull
    private String email;

    /**
     * Phone number of the user
     * It cannot be null, or have only white spaces
     * It cannot be duplicated
     * this needs to have at least 10 characters and 20 characters as max.
     */
    @Column(unique = true)
    @Size(min = 10, max = 20)
    @NotBlank
    private String phoneNumber;

    /**
     * Hashed password of the user
     * It cannot be null, or have only white spaces
     * The hash could have 60 or 80 characters using Bycript
     * This property will be ignored in the client response
     */
    @JsonIgnore
    @NotBlank
    @Size(max = 80)
    private String password;

    /**
     * State of the user: Active or inactive.
     * Default: Active
     */
    @NotNull
    private Boolean status = true;

    /**
     * Relationship with the System log table to indicates the user that do any action
     */
    @JsonIgnore
    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LogEntity> logs;

    /**
     * Relationship with the Role table to indicates permissions of the user in the system
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> roles;

    /**
     * Default role representation for the relationship
     * @return {@link List} of role names
     */
    @JsonProperty("roles")
    public List<String> getArrayRoles(){
        return this.roles.stream()
                .map(role -> role.getRole().getName())
                .collect(Collectors.toList());
    }
}
