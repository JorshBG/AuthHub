package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.utils.responses.DataResponse;
import org.springframework.http.ResponseEntity;

/**
 * Default service of user crud
 *
 * @see UserEntity
 * @see DataResponse
 */
public interface IUserService {

    /**
     * Get the user information using the username in the token
     * @return User information
     * @throws AuthHubException if the user not found
     */
    ResponseEntity<DataResponse<UserEntity>> me() throws AuthHubException;

}
