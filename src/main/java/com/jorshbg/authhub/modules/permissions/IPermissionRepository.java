package com.jorshbg.authhub.modules.permissions;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Default repository to access to the permissions table in database
 *
 * @see PermissionEntity
 */
@Repository
public interface IPermissionRepository extends IAuthHubRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByName(String name);
}
