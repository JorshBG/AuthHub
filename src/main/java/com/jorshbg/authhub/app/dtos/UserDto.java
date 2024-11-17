package com.jorshbg.authhub.app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object of the user
 * @param firstName First name of the user
 * @param lastName Last name of the user.
 * @param username Username for use it in the system.
 * @param photoUrl Photo url of the user
 * @param email Email of the user
 * @param phoneNumber Phone number of the user
 * @param password password of the user It cannot be null
 */
public record UserDto(
    @Size(min = 2, max = 50)
    @NotBlank
    String firstName,

    @Size(min = 2, max = 50)
    @NotBlank
    String lastName,

    @Size(min = 2, max = 50)
    @Column(unique = true)
    @NotBlank
    @NotNull
    String username,

    @NotBlank
    String photoUrl,

    @Column(unique = true)
    @Email
    @NotBlank
    @NotNull
    String email,

    @Column(unique = true)
    @Size(min = 10, max = 20)
    @NotBlank
    String phoneNumber,

    @NotBlank
    @Size(max = 80)
    String password
) {
}
