package com.jorshbg.authhub.modules.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndStatus(String username, Boolean status);
}
