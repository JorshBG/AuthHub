package com.jorshbg.authhub.system.exceptions;

import com.jorshbg.authhub.utils.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

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
