package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.utils.responses.DataResponse;
import com.jorshbg.authhub.utils.responses.MetadataResponse;
import com.jorshbg.authhub.utils.responses.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * User service implementation overriding the default methods
 */
@Service
public class UserServiceImpl implements IUserService {
    /**
     * Inject request class to get headers
     */
    @Autowired
    private HttpServletRequest request;
    /**
     * Inject repository user class to make queries
     */
    @Autowired
    private IUserRepository userRepository;

    /**
     * Inject jwt provider class to parse the token
     */
    @Autowired
    private JwtProvider jwtProvider;

    /**
     * Create a user
     * @param userEntity Body the user to store
     * @return User information
     * @throws AuthHubException if the user cannot be created
     */
    @Override
    public ResponseEntity<DataResponse<UserEntity>> store(UserEntity userEntity) throws AuthHubException {
        return null;
    }

    /**
     * Update a user
     * @param id Identifier of the user
     * @param userEntity Body update of the user
     * @return User information
     * @throws AuthHubException if the user not found
     */
    @Override
    public ResponseEntity<DataResponse<UserEntity>> update(UUID id, UserEntity userEntity) throws AuthHubException {
        return null;
    }

    /**
     * Deactivate user
     * @param id Identifier of the user
     * @throws AuthHubException if the user not found
     */
    @Override
    public ResponseEntity<Void> delete(UUID id) throws AuthHubException {
        return null;
    }

    /**
     * Activate user
     * @param id Identifier of the user
     * @return User information
     * @throws AuthHubException if the user not found
     */
    @Override
    public ResponseEntity<DataResponse<UserEntity>> activate(UUID id) throws AuthHubException {
        return null;
    }

    /**
     * Get the user using the id
     * @param pageable Pageable object to paginate the response
     * @return User list in a paginate response
     * @throws AuthHubException if the page is empty
     */
    @Override
    public ResponseEntity<PaginatedResponse<UserEntity>> getAll(Pageable pageable) throws AuthHubException {
        return null;
    }

    /**
     * Get the user using the id
     * @param id Identifier of the user
     * @return User information
     * @throws AuthHubException if the user not found
     */
    @Override
    public ResponseEntity<DataResponse<UserEntity>> getById(UUID id) throws AuthHubException {
        return null;
    }

    /**
     * Get the user information using the username in the token
     *
     * @return User information
     * @throws AuthHubException if the user not found
     */
    @Override
    public ResponseEntity<DataResponse<UserEntity>> me() throws AuthHubException {
        String token = request.getHeader("Authorization").substring(7);
        UserEntity user = this.userRepository.findByUsernameAndStatus(jwtProvider.getUsername(token), true).orElseThrow(
                () -> new AuthHubException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(new DataResponse<>(user, MetadataResponse.defaultResponse()));
    }
}
