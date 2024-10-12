package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends IAuthHubRepository<UserRole, UserRolesKey> {
}
