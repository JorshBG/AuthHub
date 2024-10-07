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
    public static final long ACCESS_TOKEN_EXPIRATION_TIME =  10 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME =  2 * 60 * 60 * 1000;
    private Collection<String> audiences;

    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(stringKey.getBytes(StandardCharsets.UTF_8));
        this.audiences = List.of(stringAudiences.split(","));
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

    public String getAccessToken(String subject, Map<String, Object> claims) {
        claims.put("type", "access");
        return this.generate(subject, ACCESS_TOKEN_EXPIRATION_TIME, claims);
    }

    public String getRefreshToken(String subject, Map<String, Object> claims){
        claims.put("type", "refresh");
        return this.generate(subject, REFRESH_TOKEN_EXPIRATION_TIME, claims);
    }

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

    public Jws<Claims> parse(String token) throws JwtException {
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

            String tokenType = claims.get("type", String.class);
            if(tokenType == null)
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Invalid JWT token");

            if(tokenType.equals("refresh"))
                throw new AuthHubException(HttpStatus.FORBIDDEN, "Only access tokens are allowed");

            String stringAuthorities = claims.get("Authorities", String.class);
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(stringAuthorities.split(" ")).toList().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        } catch(JwtException e) {
            throw new AuthHubException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
    }
}
