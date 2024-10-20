package com.jorshbg.authhub.system.exceptions;

import com.jorshbg.authhub.utils.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Default REST API controller for exceptions in the system. This class handle exceptions and send a response to the client
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle for no endpoint controller found
     * When a user try to access to and endpoint that doesn't exist
     * @param e Exception
     * @return Error -> Resource Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Resource Not Found",
                e.getLocalizedMessage(),
                "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), HttpStatus.NOT_FOUND);
    }

    /**
     * Handler for the missing header exception
     * If the endpoint needs to inject a header in the method and this header doesn't exist.
     * @param e Exception
     * @return Error -> Header Not Found or Missing Value
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Header Not Found or Missing Value",
                e.getLocalizedMessage(),
                "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for the custom exception class for this project
     * @param e Exception
     */
    @ExceptionHandler(AuthHubException.class)
    public ResponseEntity<ErrorResponse> handleAuthHubException(final AuthHubException e) {
        return new ResponseEntity<>(new ErrorResponse(
                null,
                e.getReason(),
                e.getStatusCode().value() >= 500? "Error" : "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), e.getStatusCode());
    }

    /**
     * Handler for the ResponseStatusException
     * @param e Exception
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleException(final ResponseStatusException e) {
        return new ResponseEntity<>(new ErrorResponse(
                null,
                e.getReason(),
                e.getStatusCode().value() >= 500? "Error" : "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), e.getStatusCode());
    }

}
