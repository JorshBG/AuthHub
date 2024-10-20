package com.jorshbg.authhub.app.controllers;

import com.jorshbg.authhub.modules.users.IUserRepository;
import com.jorshbg.authhub.modules.users.IUserService;
import com.jorshbg.authhub.modules.users.UserEntity;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.utils.responses.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for users table
 *
 * @see UserEntity
 * @see IUserRepository
 * @see JwtProvider
 */
@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    /**
     * User service injected, this injects its default implementation
     */
    @Autowired
    private IUserService userService;

    /**
     * GET - Get the default information about the user that make the request
     * @return User information
     */
    @GetMapping("me")
    public ResponseEntity<DataResponse<UserEntity>> me() {
        log.info("Initializing 'me' endpoint");
        return userService.me();
    }

}
