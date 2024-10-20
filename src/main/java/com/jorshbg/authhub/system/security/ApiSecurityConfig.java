package com.jorshbg.authhub.system.security;

import com.jorshbg.authhub.system.security.jwt.AuthenticationFilter;
import com.jorshbg.authhub.system.security.jwt.AuthorizationFilter;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.system.security.jwt.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

/**
 * API Security config, here declares the beans that must be implemented in the API
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApiSecurityConfig {

    /**
     * Redefined class UserDetailsService injected
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Injected authorization filter class to implement in the http configuration
     */
    private final AuthorizationFilter authorizationFilter;

    /**
     * Jwt provider class injected to be passed to the AuthenticationFiler
     */
    private final JwtProvider jwtProvider;

    /**
     * Configuration of the encoder to password hashing
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Filter configuration chain for all the API REST, this configuration must be edited only if is necessary and validate all the changes executing testing over all the endpoints
     * @param http Security object
     * @param manager Authentication manager configuration
     * @return {@link SecurityFilterChain}
     * @throws Exception If fails to create the configuration
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(jwtProvider);
        authFilter.setAuthenticationManager(manager);
        authFilter.setFilterProcessesUrl("/auth/login");

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorized -> {
           authorized.requestMatchers("/auth/login").permitAll();
           authorized.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
           authorized.anyRequest().authenticated();
        });
        http.exceptionHandling(handler -> {
           handler.accessDeniedHandler(deniedHandler());
        });
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilter(authFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(cors -> cors.configurationSource(apiCors()));
        return http.build();
    }

    /**
     * Generate bean of the Denied handler
     * @return {@link AccessDeniedHandler}
     */
    @Bean
    AccessDeniedHandler deniedHandler(){
        return new DeniedHandler();
    }

    /**
     * Configuration of the API Cross Origin
     * @return {@link CorsConfigurationSource}
     */
    CorsConfigurationSource apiCors() {
        CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8088"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
    }

    /**
     * configuration of the authentication requirements
     * @param passwordEncoder Hashing object for passwords
     * @param userDetailsService Redefined UserDetailsService
     * @return {@link AuthenticationManager}
     * @throws Exception if the provider fails to create the manager
     */
    @Bean
    AuthenticationManager authManager(
            PasswordEncoder passwordEncoder,
            UserDetailsServiceImpl userDetailsService
    ) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

}
