package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.app.dtos.UserDetailsDto;
import com.jorshbg.authhub.app.dtos.UserDto;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.utils.request_params.UserGetParams;
import com.jorshbg.authhub.utils.responses.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
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
    private final HttpServletRequest request;
    /**
     * Inject repository user class to make queries
     */
    private final IUserRepository userRepository;

    /**
     * Inject jwt provider class to parse the token
     */
    private final JwtProvider jwtProvider;

    public UserService(HttpServletRequest request, IUserRepository userRepository, JwtProvider jwtProvider) {
        this.request = request;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


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
        pageNumber = Math.max(pageNumber, 0);
        size = Math.max(size, 10);
        var pageable = PageRequest.of(pageNumber, size, Sort.by(direction, orderBy));
        var page = this.userRepository.findAll(pageable);
        if(page.getTotalPages() == 0) throw new AuthHubException(HttpStatus.NOT_FOUND, "No pages found");
        if(pageNumber > page.getTotalPages()) throw new AuthHubException(HttpStatus.NOT_FOUND, "No more pages");
        if(page.getTotalElements() == 0) throw new AuthHubException(HttpStatus.NOT_FOUND, "No more elements");
        return AuthHubResponse.paginated(page);
    }

    public ResponseEntity<DataResponse<List<UserEntity>>> getAllAsList(UserGetParams params) {
        List<UserEntity> users = userRepository.findAll();
        if(users.isEmpty()) throw new AuthHubException(HttpStatus.NOT_FOUND, "Users not found");
        return AuthHubResponse.data(users);
    }

    public ResponseEntity<DataResponse<UserEntity>> store(UserDto dto) throws AuthHubException {
        var user = IUserMapper.INSTANCE.toEntity(dto);
        var saved = this.userRepository.save(user);
        return AuthHubResponse.data(saved, HttpStatus.CREATED);
    }
}
