package com.jorshbg.authhub.app.controllers;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.Jwt;
import com.jorshbg.authhub.system.security.jwt.UserDetailsImpl;
import com.jorshbg.authhub.system.security.jwt.UserDetailsServiceImpl;
import com.jorshbg.authhub.utils.responses.JwtResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private Jwt jwt;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestHeader("Authorization") String token) {

        if (token == null)
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "No token provided");

        if (!token.startsWith("Bearer "))
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");
        try {

            token = token.substring(7);
            String tokenType = jwt.parse(token).getPayload().get("type", String.class);

            if (tokenType == null)
                throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");

            if (!tokenType.equals("refresh"))
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Only refresh tokens are allowed here");

            String username = jwt.parse(token).getPayload().getSubject();

            var details = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

            var response = new JwtResponse(
                    jwt.getAccessToken(username, new LinkedHashMap<>(Map.of(
                            "Authorities", details.getAuthoritiesAsString()
                    ))),
                    "bearer",
                    Jwt.ACCESS_TOKEN_EXPIRATION_TIME,
                    jwt.getRefreshToken(username, new LinkedHashMap<>()), null
            );

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }


    }

    @PostMapping("permanent_token")
    public ResponseEntity<JwtResponse> permanentToken(@RequestHeader("Authorization") String token) {
        if (!token.startsWith("Bearer "))
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");

        try {

            token = token.substring(7);
            String tokenType = jwt.parse(token).getPayload().get("type", String.class);

            if (tokenType == null)
                throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");

            if (!tokenType.equals("access"))
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Invalid token");

            String username = jwt.parse(token).getPayload().getSubject();

            var details = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

            var response = new JwtResponse(
                    jwt.getPermanentToken(username, new LinkedHashMap<>(Map.of(
                            "Authorities", details.getAuthoritiesAsString()
                    ))),
                    "bearer",
                    null,
                    null,
                    "This token is permanent and it's your responsibility take safe it"
            );

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}
