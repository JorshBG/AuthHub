package com.jorshbg.authhub.system.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AuthHubException extends ResponseStatusException {

    public AuthHubException(HttpStatusCode status) {
        this(status, null);
    }

    public AuthHubException(HttpStatusCode status, String reason) {
        super(status, reason);
        log.error("A error has been detected with code {} with a reason: {}", status.value(), reason);
    }

    public AuthHubException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
        log.error("A error in the system has been thrown", cause);
    }
}
