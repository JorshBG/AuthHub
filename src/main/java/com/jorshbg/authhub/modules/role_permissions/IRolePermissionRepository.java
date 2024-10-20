package com.jorshbg.authhub.modules.role_permissions;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

/**
 * Default repository to access to the relationship between users and roles in database
 *
 * @see RolePermission
 * @see RolePermissionsKey
 */
@Repository
public interface IRolePermissionRepository extends IAuthHubRepository<RolePermission, RolePermissionsKey> {
}
