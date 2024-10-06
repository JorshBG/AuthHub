package com.jorshbg.authhub.modules.role_permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolePermissionRepository extends JpaRepository<RolePermission, RolePermissionsKey> {
}
