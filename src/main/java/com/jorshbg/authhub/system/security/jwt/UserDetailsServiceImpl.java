package com.jorshbg.authhub.system.security.jwt;

import com.jorshbg.authhub.modules.users.IUserRepository;
import com.jorshbg.authhub.modules.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailsService implementation to query a user and load his data.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Injected repository of the user
     */
    @Autowired
    private IUserRepository userRepository;

    /**
     * Load information about the user
     * @param username String name of the user
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException if the user has not been found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameAndStatus(username, true)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new UserDetailsImpl(user);
    }

}
