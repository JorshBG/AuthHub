package com.jorshbg.authhub.system.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorshbg.authhub.app.dtos.Auth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Jwt jwt;

    public AuthenticationFilter(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Auth credentials = new Auth();
        try {
            credentials = new ObjectMapper().readValue(request.getReader(), Auth.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.emptyList());

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String token = jwt.temporalToken(userDetails.getUsername(), new LinkedHashMap<>(Map.of(
                "Authorities", userDetails.getAuthoritiesAsString()
        )));
        response.addHeader("Authorization", "Bearer " + token);
        var obj = new LinkedHashMap<>(Map.of(
                "token", token,
                "type", "bearer",
                "expiresIn", Jwt.TEMPORAL_EXPIRATION_TIME
        ));
        response.getWriter().write(new ObjectMapper().writeValueAsString(obj));
        response.setContentType("application/json");
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + failed.getMessage() + "\"}");
        response.getWriter().flush();
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
