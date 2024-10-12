package com.jorshbg.authhub.modules.roles;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRoleRepository extends IAuthHubRepository<RoleEntity, UUID> {
}
