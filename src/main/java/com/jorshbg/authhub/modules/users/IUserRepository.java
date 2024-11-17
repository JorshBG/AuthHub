package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.modules.common.IAuthHubRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Default repository to access to the users table in database
 *
 * @see UserEntity
 */
@Repository
public interface IUserRepository extends IAuthHubRepository<UserEntity, UUID> {

    /**
     * Find one user using his username and the status
     * @param username Name of the users
     * @param status State: Active or inactive
     * @return Optional to manage the response and trow an exception if the user not found
     */
    Optional<UserEntity> findByUsernameAndStatus(String username, Boolean status);

    Optional<UserEntity> findByEmailAndStatus(String mail, Boolean b);
}
