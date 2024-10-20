package com.jorshbg.authhub.modules.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

/**
 * Class to define the table in database system -> roles
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class RoleEntity {

    /**
     * Default random UUID to use as id in the table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Name of the role
     * the maximum of the character is 15
     * the name of the role is unique, cannot duplicate it
     */
    @Column(unique = true)
    @NotBlank
    @Size(max = 15)
    @NotNull
    private String name;

    /**
     * Relationship with the users table
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private List<UserRole> users;

    /**
     * Relationship with the permissions table
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private List<RolePermission> permissions;

}
