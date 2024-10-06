package com.jorshbg.authhub.system.security.jwt;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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

@Component
@Slf4j
public class Jwt {

    @Value("${jwt.key}")
    private String stringKey;

    @Value("${jwt.audiences}")
    private String stringAudiences;

    @Value("${jwt.issuer}")
    private String issuer;

    private SecretKey key;
    public static final long TEMPORAL_EXPIRATION_TIME =  5 * 60 * 1000;
    private static final long REFRESH_EXPIRATION_TIME =  2 * 60 * 60 * 1000;
    private Collection<String> audiences;

    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(stringKey.getBytes(StandardCharsets.UTF_8));
        this.audiences = List.of(stringAudiences.split(","));
    }

    private String generate(String subject, long expirationTime) {
        return this.generate(subject, expirationTime, null);
    }

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
                .audience().add(audiences).and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .issuer(issuer)
                .signWith(key)
                .compact();
    }

    public String extendedToken(String token){
        Jws<Claims> parsed = this.parse(token);
        return this.extendedToken(parsed.getPayload().getSubject(),  parsed.getPayload().get("user", Map.class));
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    public boolean validate(String token) {
        return this.validateAudience(token) && !this.isExpired(token);
    }

    public boolean validateAudience(String token){
        Jws<Claims> parsed = this.parse(token);
        for(String audience : audiences) {
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
