package com.jorshbg.authhub.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
