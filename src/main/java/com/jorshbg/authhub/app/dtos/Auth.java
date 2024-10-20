package com.jorshbg.authhub.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for the authentication user credentials
 * @param password Password of the user
 * @param username Name of the user
 */
public record Auth(
        @Size(min = 1, max = 50)
        @NotBlank
        String password,
        @NotBlank
        @Size(min = 2, max = 50)
        String username
) {}
