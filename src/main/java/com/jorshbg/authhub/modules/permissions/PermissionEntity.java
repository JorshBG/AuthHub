package com.jorshbg.authhub.modules.permissions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
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
 * Class to define the table in database system -> permissions
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "permissions")
public class PermissionEntity {

    /**
     * Default random UUID to use as id in the table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Name of the permissions
     * the maximum of the character is 15
     * the name of the role is unique, cannot duplicate it
     */
    @NotBlank
    @Column(unique = true)
    @NotNull
    @Size(max = 20)
    private String name;

    /**
     * Relationship with the roles table
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "permission")
    @JsonIgnore
    private List<RolePermission> roles;

}
