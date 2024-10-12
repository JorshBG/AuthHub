package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends IAuthHubRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndStatus(String username, Boolean status);
}
