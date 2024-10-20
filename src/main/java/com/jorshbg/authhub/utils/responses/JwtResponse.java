package com.jorshbg.authhub.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for token response to the client
 * @param accessToken Access auth token
 * @param tokenType Default Bearer type token
 * @param expiresIn Datetime expires
 * @param refreshToken Token to make refresh
 * @param warning Alert for client. Apply only in permanent token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtResponse(
        @JsonProperty(value = "access_token")
        String accessToken,
        @JsonProperty(value = "token_type")
        String tokenType,
        @JsonProperty(value = "expires_in")
        Long expiresIn,
        @JsonProperty(value = "refresh_token")
        String refreshToken,
        String warning
) {}
