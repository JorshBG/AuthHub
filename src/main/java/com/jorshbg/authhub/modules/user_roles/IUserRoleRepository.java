package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

/**
 * Default repository to access to the relationship between users and roles in database
 *
 * @see UserRole
 * @see UserRolesKey
 */
@Repository
public interface IUserRoleRepository extends IAuthHubRepository<UserRole, UserRolesKey> {
}
