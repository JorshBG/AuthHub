package com.jorshbg.authhub.system.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private Jwt jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);
        if (token != null) {
            UsernamePasswordAuthenticationToken authenticationToken = jwt.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Invalid authorization provided\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }

    String getToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
