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

/**
 * Filter to authenticate the user in the system extending  the UsernamePasswordAuthenticationFilter
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Injected from {@link com.jorshbg.authhub.system.security.ApiSecurityConfig ApiSecurityConfig} token provider class
     */
    private final JwtProvider jwtProvider;

    /**
     * Default constructor to inject the token provider
     * @param jwtProvider Token provider class
     */
    public AuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Method to handle the attempt of the user authentication
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OIDC).
     * @return Authentication object
     * @throws AuthenticationException If the user doesn't sign in the system
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Auth credentials = new Auth(null, null);
        try {
            credentials = new ObjectMapper().readValue(request.getReader(), Auth.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                credentials.email(),
                credentials.password(),
                Collections.emptyList());

        return getAuthenticationManager().authenticate(auth);
    }

    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String accessToken = jwtProvider.getAccessToken(userDetails.getUsername(), new LinkedHashMap<>(Map.of(
                "Authorities", userDetails.getAuthoritiesAsString()
        )));
        String refreshToken = jwtProvider.getRefreshToken(userDetails.getUsername(), new LinkedHashMap<>());
        response.addHeader("Authorization", "Bearer " + accessToken);
        var obj = new LinkedHashMap<>();
        obj.put("access_token", accessToken);
        obj.put("token_type", "bearer");
        obj.put("expires_in", JwtProvider.ACCESS_TOKEN_EXPIRATION_TIME);
        obj.put("refresh_token", refreshToken);
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
    }
}
