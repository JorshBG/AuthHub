package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.utils.responses.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRolesService {
    public ResponseEntity<DataResponse<UserRole>> assignRole(UUID userId) {
        return null;
    }

    public ResponseEntity<DataResponse<UserRole>> removeRole(UUID userId) {
        return null;
    }
}
