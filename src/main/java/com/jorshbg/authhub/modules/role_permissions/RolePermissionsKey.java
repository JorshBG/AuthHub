package com.jorshbg.authhub.modules.role_permissions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Composed primary key for the relationship between permissions and roles
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class RolePermissionsKey implements Serializable {

    /**
     * The id of the role.
     * role_id will be the name in the table
     */
    @Column(name = "role_id")
    private UUID roleId;

    /**
     * The id of the permissions.
     * permission_id will be the name in the table
     */
    @Column(name = "permission_id")
    private UUID permissionId;

}
