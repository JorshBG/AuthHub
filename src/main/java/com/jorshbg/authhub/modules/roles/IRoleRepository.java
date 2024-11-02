package com.jorshbg.authhub.modules.roles;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Default repository to access to the roles table in database
 *
 * @see RoleEntity
 */
@Repository
public interface IRoleRepository extends IAuthHubRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String name);
}
