package com.jorshbg.authhub.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Resource not found");
        errorResponse.put("path", e.getRequestURL());
        errorResponse.put("httpMethod", e.getHttpMethod());
        errorResponse.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
