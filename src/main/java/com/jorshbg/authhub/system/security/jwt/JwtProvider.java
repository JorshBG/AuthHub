package com.jorshbg.authhub.system.security.jwt;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Json Web Token provider class
 * It can generate, validate, and set and authentication for the system
 */
@Component
@Slf4j
public class JwtProvider {
    /**
     * Unencrypted key of the JWT used to sign the token
     */
    @Value("${jwt.key}")
    private String stringKey;
    /**
     * Valid domains to use the jwt
     */
    @Value("${jwt.audiences}")
    private String stringAudiences;
    /**
     * Domain that emits the jwt
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * Encrypted key to sign the token
     */
    private SecretKey key;
    /**
     * Default token expiration time for the usable token
     */
    public static final long ACCESS_TOKEN_EXPIRATION_TIME =  10 * 60 * 1000;
    /**
     * Expiration time for the refresh token
     */
    private static final long REFRESH_TOKEN_EXPIRATION_TIME =  2 * 60 * 60 * 1000;
    /**
     * Collection of valid domains to use the token
     */
    private Collection<String> audiences;

    /**
     * Initialize the secret key and the audiences.
     */
    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(stringKey.getBytes(StandardCharsets.UTF_8));
        this.audiences = List.of(stringAudiences.split(","));
    }

    /**
     * Generate a JWT, can be refresh token or access token
     * @param subject Name of the user
     * @param expirationTime Default expiration time
     * @param claims Additional values in the jwt
     * @return A JWT in string format
     */
    private String generate(String subject, long expirationTime, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .audience().add(audiences).and()
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .issuer(issuer)
                .signWith(key)
                .compact();
    }

    /**
     * Generate access token
     * @param subject Name of the user
     * @param claims Additional values in the jwt
     * @return A JWT in string format
     */
    public String getAccessToken(String subject, Map<String, Object> claims) {
        claims.put("type", "access");
        return this.generate(subject, ACCESS_TOKEN_EXPIRATION_TIME, claims);
    }

    /**
     * Generate refresh token
     * @param subject Name of the user
     * @param claims Additional values in the jwt
     * @return A JWT in string format
     */
    public String getRefreshToken(String subject, Map<String, Object> claims){
        claims.put("type", "refresh");
        return this.generate(subject, REFRESH_TOKEN_EXPIRATION_TIME, claims);
    }

    /**
     * Generate a permanent token
     * @param subject Name of the user
     * @param claims Additional values in the jwt
     * @return A JWT in string format
     */
    public String getPermanentToken(String subject, Map<String, Object> claims){
        claims.put("type", "permanent");
        return Jwts.builder()
                .subject(subject)
                .audience().add(audiences).and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .issuer(issuer)
                .signWith(key)
                .compact();
    }

    /**
     * Get the claims into the JWT
     * @param token String format json web token
     * @return Claims of the token
     * @throws JwtException if the token cannot be parsed
     */
    public Jws<Claims> parse(String token) throws JwtException {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    /**
     * Get the email of the user
     * @param token JWT token valid
     * @return The email
     * @throws JwtException If the token is invalid or expired
     */
    public String getSubject(String token) throws JwtException {
        return this.parse(token).getPayload().getSubject();
    }

    /**
     * Validate audiences of the token
     * @param token String format json web token
     * @return true - if the token has a valid audience otherwise, return false
     * @throws JwtException if the token cannot be parsed
     */
    public boolean validateAudience(String token) throws JwtException {
        Jws<Claims> parsed = this.parse(token);
        for(String audience : audiences) {
            if (parsed.getPayload().getAudience().contains(audience)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate the expiration date
     * @param token String format json web token
     * @return true - if the token has a valid expiration date, return false
     */
    public boolean isExpired(String token) throws JwtException {
        return this.parse(token).getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
    }

    /**
     * Validate a token and generate an authentication for the user that is trying to access to the resources
     * @param token String format json web token
     * @return Authentication for the user
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = this.parse(token).getPayload();

            if(!this.validateAudience(token))
                throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");

            String tokenType = claims.get("type", String.class);
            if(tokenType == null)
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Invalid JWT token");

            if(tokenType.equals("refresh"))
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Only access tokens are allowed");

            if(!tokenType.equals("permanent")){
                if(this.isExpired(token))
                    throw new AuthHubException(HttpStatus.FORBIDDEN, "Expired JWT token");
            }

            String stringAuthorities = claims.get("Authorities", String.class);
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(stringAuthorities.split(" ")).toList().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        } catch(JwtException e) {
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
    }
}
