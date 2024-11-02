package com.jorshbg.authhub.system.security.jwt;

import com.jorshbg.authhub.modules.users.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Default implementation of UserDetails
 */
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    /**
     * User object entity to get his data in the methods
     */
    private final UserEntity user;

    /**
     * Get all authorities and collect in a List of names as string
     * @return Collection of SimpleGrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());
    }

    /**
     * Get the password of the user
     * @return Hashed password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get the email as string
     * @return String of email
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Get all authorities and collect in an Array of names as string
     * @return Array of authority names
     */
    public String getAuthoritiesAsString() {
        StringBuilder builder = new StringBuilder();
        user.getRoles().forEach(role -> builder.append(role.getRole().getName()).append(" "));
        return builder.toString();
    }
}
