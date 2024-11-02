package com.jorshbg.authhub.modules.roles;

import com.jorshbg.authhub.app.dtos.RoleDto;
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
public class RoleService extends AuthHubCrudService<RoleEntity, RoleDto> {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public ResponseEntity<List<RoleEntity>> getAll() {
        List<RoleEntity> roles = roleRepository.findAll();
        if(roles.isEmpty()) throw new AuthHubException(HttpStatus.NOT_FOUND, "Roles not found");
        return ResponseEntity.ok(roles);
    }

    @Override
    public ResponseEntity<DataResponse<RoleEntity>> getById(UUID id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Role not found"));
        return ResponseEntity.ok(new DataResponse<>(role, MetadataResponse.defaultResponse()));
    }

    @Override
    public ResponseEntity<DataResponse<RoleEntity>> create(RoleDto entity, URI location) {
        RoleEntity role = new RoleEntity(null, entity.name(), null, null);
        RoleEntity exists = this.roleRepository.findByName(entity.name()).orElse(null);
        if(exists != null) throw new AuthHubException(HttpStatus.CONFLICT, "Role name already exists");
        var saved = this.roleRepository.save(role);
        return ResponseEntity.created(location).body(new DataResponse<>(saved, MetadataResponse.defaultResponse()));
    }

    @Override
    public ResponseEntity<DataResponse<RoleEntity>> update(UUID id, RoleDto entity) {
        RoleEntity role = this.roleRepository.findById(id).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Role not found"));
        RoleEntity exists = this.roleRepository.findByName(entity.name()).orElse(null);
        if(exists != null && (exists.getId() != role.getId())) throw new AuthHubException(HttpStatus.CONFLICT, "Role name already exists");
        role.setName(entity.name());
        var updated = this.roleRepository.save(role);
        return ResponseEntity.ok().body(new DataResponse<>(updated, MetadataResponse.defaultResponse()));
    }

    @Override
    public ResponseEntity<DataResponse<RoleEntity>> delete(UUID id) {
        RoleEntity role = this.roleRepository.findById(id).orElseThrow(() -> new AuthHubException(HttpStatus.NOT_FOUND, "Role not found"));
        this.roleRepository.delete(role);
        return ResponseEntity.ok().body(new DataResponse<>(role, MetadataResponse.defaultResponse()));
    }
}
