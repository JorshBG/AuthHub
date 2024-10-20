package com.jorshbg.authhub.modules.role_permissions;

import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Relationship between roles and permissions
 * This class will generate a new table to store records about this relation
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_has_permissions")
public class RolePermission {
    /**
     * Composed key
     */
    @EmbeddedId
    private RolePermissionsKey id;

    /**
     * Mapped key to create a relation with the roles table
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    /**
     * Mapped key to create a relation with the permissions table
     */
    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;

}
