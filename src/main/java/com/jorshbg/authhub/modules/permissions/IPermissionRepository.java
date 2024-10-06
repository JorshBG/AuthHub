package com.jorshbg.authhub.modules.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
