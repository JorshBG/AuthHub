package com.jorshbg.authhub.system.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

/**
 * Denied handler for denied requests
 */
@Slf4j
public class DeniedHandler implements AccessDeniedHandler {


    /**
     * Handler to check the authorities and authentication of the user and denied if it is necessary.
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException If cannot access to the request
     * @throws ServletException If the server cannot produce an error
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission to access this resource");
        }
    }
}
