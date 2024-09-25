package com.jorshbg.authhub.modules.role_permissions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class RolePermissionsKey implements Serializable {

    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "permission_id")
    private UUID PermissionId;

}
