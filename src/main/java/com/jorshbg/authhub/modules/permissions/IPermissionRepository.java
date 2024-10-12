package com.jorshbg.authhub.modules.permissions;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends IAuthHubRepository<PermissionEntity, Long> {
}
