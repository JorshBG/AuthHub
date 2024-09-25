package com.jorshbg.authhub.system.security.jwt;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Jwt {

    private static final SecretKey KEY = Jwts.SIG.HS256.key().build();
    private static final long TEMPORAL_EXPIRATION_TIME =  5 * 60 * 1000;
    private static final long REFRESH_EXPIRATION_TIME =  2 * 60 * 60 * 1000;
    private static final Collection<String> AUDIENCES = List.of("http://localhost:4200");

    private String generate(String subject, long expirationTime) {
        return this.generate(subject, expirationTime, null);
    }

    private String generate(String subject, long expirationTime, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .audience().add("http://localhost:4200").and()
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .issuer("http://localhost:8088/api/v1")
                .signWith(KEY)
                .compact();
    }

    public String temporalToken(String subject, Map<String, Object> claims) {
        return this.generate(subject, TEMPORAL_EXPIRATION_TIME, claims);
    }

    public String refreshToken(String token){
        Jws<Claims> parsed = this.parse(token);
        return this.generate(parsed.getPayload().getSubject(), REFRESH_EXPIRATION_TIME, parsed.getPayload().get("user", Map.class));
    }

    public String extendedToken(String subject, Map<String, Object> claims){
        claims.put("extended", "true");
        return Jwts.builder()
                .subject(subject)
                .audience().add(AUDIENCES).and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .issuer("http://localhost:8088/api/v1")
                .signWith(KEY)
                .compact();
    }

    public String extendedToken(String token){
        Jws<Claims> parsed = this.parse(token);
        return this.extendedToken(parsed.getPayload().getSubject(),  parsed.getPayload().get("user", Map.class));
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
    }

    public boolean validate(String token) {
        return this.validateAudience(token) && !this.isExpired(token);
    }

    public boolean validateAudience(String token){
        Jws<Claims> parsed = this.parse(token);
        for(String audience : AUDIENCES) {
            if (parsed.getPayload().getAudience().contains(audience)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExpired(String token){
        return this.parse(token).getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = this.parse(token).getPayload();
            if(!this.validate(token))
                throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");

            String stringAuthorities = claims.get("Authorities", String.class);
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(stringAuthorities.split(" ")).toList().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        } catch(JwtException e) {
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
    }
}
