package com.jorshbg.authhub.modules.role_permissions;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolePermissionRepository extends IAuthHubRepository<RolePermission, RolePermissionsKey> {
}
