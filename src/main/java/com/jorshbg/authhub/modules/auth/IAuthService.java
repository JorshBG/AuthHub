package com.jorshbg.authhub.modules.auth;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.utils.responses.JwtResponse;
import org.springframework.http.ResponseEntity;

/**
 * Service for the authentication request like refresh and permanent token
 */
public interface IAuthService {
    /**
     * Refresh the access token of the user
     * @return New token and refresh token
     * @throws AuthHubException if the token is invalid
     */
    ResponseEntity<JwtResponse> refreshToken() throws AuthHubException;

    /**
     * Get a permanent token - No expiration date
     * @return Permanent token
     * @throws AuthHubException if the access token is invalid
     */
    ResponseEntity<JwtResponse> permanentToken() throws AuthHubException;

}
