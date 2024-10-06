package com.jorshbg.authhub.modules.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
}
