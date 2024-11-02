package com.jorshbg.authhub.modules.common;

import com.jorshbg.authhub.utils.responses.DataResponse;
import com.jorshbg.authhub.utils.responses.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public abstract class AuthHubCrudService<E, DTO> {

    public ResponseEntity<List<E>> getAll(){
        return ResponseEntity.ok(List.of());
    }

    public ResponseEntity<PaginatedResponse<E>> getAll(Pageable pageable){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<DataResponse<E>> getById(final UUID id){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<DataResponse<E>> create(final DTO entity, final URI location){
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<DataResponse<E>> update(final UUID id, final DTO entity){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<DataResponse<E>> delete(final UUID id){
        return ResponseEntity.ok().build();
    }

}
