package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.app.dtos.UserDetailsDto;
import com.jorshbg.authhub.app.dtos.UserDto;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.utils.request_params.UserGetParams;
import com.jorshbg.authhub.utils.responses.DataResponse;
import com.jorshbg.authhub.utils.responses.MetadataResponse;
import com.jorshbg.authhub.utils.responses.PaginatedResponse;
import com.jorshbg.authhub.utils.responses.RecoverPasswordResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * User service implementation overriding the default methods
 */
@Service
public class UserService {
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
     * Get the user information using the username in the token
     *
     * @return User information
     * @throws AuthHubException if the user not found
     */
    public ResponseEntity<DataResponse<UserEntity>> me() throws AuthHubException {
        String token = request.getHeader("Authorization").substring(7);
        UserEntity user = this.userRepository.findByUsernameAndStatus(jwtProvider.getSubject(token), true).orElseThrow(
                () -> new AuthHubException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(new DataResponse<>(user, MetadataResponse.defaultResponse()));
    }

    public ResponseEntity<RecoverPasswordResponse> attemptChangePassword() throws AuthHubException {
        return null;
    }

    public ResponseEntity<DataResponse<UserEntity>> changePassword(String tokenEmail) throws AuthHubException {
        return null;
    }

    public ResponseEntity<DataResponse<UserEntity>> update(UUID id, UserDetailsDto dto) {
        return null;
    }

    public ResponseEntity<PaginatedResponse<UserEntity>> getAllAsPage(UserGetParams params, int pageNumber, int size, String orderBy, Sort.Direction direction) {
        return null;
    }

    public ResponseEntity<List<UserEntity>> getAllAsList(UserGetParams params) {
        return null;
    }

    public ResponseEntity<DataResponse<UserEntity>> store(UserDto dto) {
        return null;
    }
}
