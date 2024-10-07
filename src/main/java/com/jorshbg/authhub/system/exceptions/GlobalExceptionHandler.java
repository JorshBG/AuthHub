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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Resource Not Found",
                e.getLocalizedMessage(),
                "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Header Not Found or Missing Value",
                e.getLocalizedMessage(),
                "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthHubException.class)
    public ResponseEntity<ErrorResponse> handleAuthHubException(final AuthHubException e) {
        return new ResponseEntity<>(new ErrorResponse(
                null,
                e.getReason(),
                e.getStatusCode().value() >= 500? "Error" : "Warning",
                LocalDateTime.now(ZoneId.systemDefault())
        ), e.getStatusCode());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleException(final ResponseStatusException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getReason(),
                e.getLocalizedMessage(),
                e.getStatusCode().toString(),
                LocalDateTime.now(ZoneId.systemDefault())
        ), e.getStatusCode());
    }

}
