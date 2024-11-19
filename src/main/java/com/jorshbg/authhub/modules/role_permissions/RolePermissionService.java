package com.jorshbg.authhub.modules.role_permissions;

import com.jorshbg.authhub.utils.responses.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RolePermissionService {
    public ResponseEntity<DataResponse<RolePermission>> store(UUID roleId, UUID permissionId) {
        return null;
    }

    public ResponseEntity<DataResponse<RolePermission>> removePermission(UUID permissionId) {
        return null;
    }

    public ResponseEntity<List<RolePermission>> getAllAsList() {
        return null;
    }

    public ResponseEntity<List<RolePermission>> getPermissions(UUID roleId) {
        return null;
    }
}
