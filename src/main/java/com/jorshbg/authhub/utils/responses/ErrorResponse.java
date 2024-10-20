package com.jorshbg.authhub.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * Default error response
 * @param error Type of error
 * @param message Description of error
 * @param level Level of error. Warning, Error, Debug, Danger
 * @param timestamp Datetime of the response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String error,
        String message,
        String level,
        LocalDateTime timestamp
) {}
