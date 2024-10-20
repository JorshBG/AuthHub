package com.jorshbg.authhub.app.controllers;

import com.jorshbg.authhub.modules.auth.IAuthService;
import com.jorshbg.authhub.utils.responses.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication request
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {
    /**
     * Inject default implementation of the service
     */
    @Autowired
    private IAuthService authService;

    /**
     * Refresh the access token
     * @return JwtResponse with the new tokens
     */
    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refresh() {
        log.info("Initializing 'refresh' endpoint");
        return this.authService.refreshToken();
    }

    /**
     * Get a permanent token without expiration date
     * @return JwtResponse with the permanent token
     */
    @PostMapping("permanent_token")
    public ResponseEntity<JwtResponse> permanentToken() {
        log.info("Initializing 'permanent_token' endpoint");
        return this.authService.permanentToken();
    }
}
