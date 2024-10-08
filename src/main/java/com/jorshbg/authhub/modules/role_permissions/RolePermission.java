package com.jorshbg.authhub.modules.role_permissions;

import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_has_permissions")
public class RolePermission {

    @EmbeddedId
    private RolePermissionsKey id;

    public RolePermission(final RolePermissionsKey id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;

}
