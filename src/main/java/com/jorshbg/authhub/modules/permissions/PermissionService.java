package com.jorshbg.authhub.modules.permissions;

import com.jorshbg.authhub.app.dtos.PermissionDto;
import com.jorshbg.authhub.modules.common.AuthHubCrudService;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.utils.responses.DataResponse;
import com.jorshbg.authhub.utils.responses.MetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class PermissionService extends AuthHubCrudService<PermissionEntity, PermissionDto> {

    @Autowired
    private IPermissionRepository repository;

    @Override
    public ResponseEntity<List<PermissionEntity>> getAll() {
        var list = repository.findAll();
        if(list.isEmpty()) throw new AuthHubException(HttpStatus.NOT_FOUND, "No permissions found");
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<DataResponse<PermissionEntity>> create(PermissionDto entity, URI location) {
        PermissionEntity exists = this.repository.findByName(entity.name()).orElse(null);
        if(exists != null) throw new AuthHubException(HttpStatus.CONFLICT, "Permission name already exists");
        var saved = this.repository.save(new PermissionEntity(null, entity.name(), null));
        return ResponseEntity.created(location).body(new DataResponse<>(saved, MetadataResponse.defaultResponse()));
    }

    @Override
    public ResponseEntity<DataResponse<PermissionEntity>> update(UUID id, PermissionDto entity) {
        PermissionEntity toUpdate = this.repository.findById(id).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Permission name not found"));
        PermissionEntity exists = this.repository.findByName(entity.name()).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Permission name not found"));
        if(exists.getId() != toUpdate.getId()) throw new AuthHubException(HttpStatus.CONFLICT, "Permission name already exists");
        toUpdate.setName(entity.name());
        var updated = this.repository.save(toUpdate);
        return ResponseEntity.ok(new DataResponse<>(updated, MetadataResponse.defaultResponse()));
    }

    @Override
    public ResponseEntity<DataResponse<PermissionEntity>> delete(UUID id) {
        var exists = this.repository.findById(id).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Permission name not found"));
        this.repository.delete(exists);
        return ResponseEntity.ok(new DataResponse<>(exists, MetadataResponse.defaultResponse()));
    }
}
