package com.jorshbg.authhub.modules.auth;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.system.security.jwt.UserDetailsImpl;
import com.jorshbg.authhub.system.security.jwt.UserDetailsServiceImpl;
import com.jorshbg.authhub.utils.responses.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of the {@link IAuthService}
 */
@Service
public class AuthServiceImpl implements IAuthService {
    /**
     * Inject Jwt provider for generate new tokens and parse the current in the header
     */
    @Autowired
    private JwtProvider jwtProvider;
    /**
     * Inject implementation of the user details to get the information
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    /**
     * Inject request for get the token in the header
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * Refresh the access token of the user
     * @return New token and refresh token
     * @throws AuthHubException if the token is invalid
     */
    @Override
    public ResponseEntity<JwtResponse> refreshToken() throws AuthHubException {
        String token = request.getHeader("Authorization");

        this.validateTypeToken(token, "refresh");

        String username = jwtProvider.parse(token).getPayload().getSubject();

        var details = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        var response = new JwtResponse(
                jwtProvider.getAccessToken(username, new LinkedHashMap<>(Map.of(
                        "Authorities", details.getAuthoritiesAsString()
                ))),
                "Bearer",
                JwtProvider.ACCESS_TOKEN_EXPIRATION_TIME,
                jwtProvider.getRefreshToken(username, new LinkedHashMap<>()), null
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Get a permanent token - No expiration date
     * @return Permanent token
     * @throws AuthHubException if the access token is invalid
     */
    @Override
    public ResponseEntity<JwtResponse> permanentToken() {
        String token = request.getHeader("Authorization");

        this.validateTypeToken(token, "access");

        String username = jwtProvider.parse(token).getPayload().getSubject();

        var details = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        var response = new JwtResponse(
                jwtProvider.getPermanentToken(username, new LinkedHashMap<>(Map.of(
                        "Authorities", details.getAuthoritiesAsString()
                ))),
                "Bearer",
                null,
                null,
                "This token is permanent and it's your responsibility take safe it"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Validate if the token provided has the correct type
     * @param token JWT in string format
     * @param typeRequired Type of the jwt
     * @throws AuthHubException if the token is invalid for the request
     */
    private void validateTypeToken(String token, String typeRequired) throws AuthHubException {
        String tokenType = jwtProvider.parse(token).getPayload().get("type", String.class);

        if (tokenType == null)
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid token");

        if (!tokenType.equals(typeRequired))
            throw new AuthHubException(HttpStatus.FORBIDDEN, "Only "+typeRequired+" tokens are allowed here");
    }

}
