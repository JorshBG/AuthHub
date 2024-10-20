package com.jorshbg.authhub.modules.common;

import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.utils.responses.DataResponse;
import com.jorshbg.authhub.utils.responses.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

/**
 * CRUD default functions for services
 * @param <T> Entity class
 * @param <DTO> Data transfer object class
 */
public interface IAuthHubCRUDService<T, DTO> {

    ResponseEntity<DataResponse<T>> store(DTO dto) throws AuthHubException;

    ResponseEntity<DataResponse<T>> update(UUID id, DTO dto) throws AuthHubException;

    ResponseEntity<Void> delete(UUID id) throws AuthHubException;

    ResponseEntity<DataResponse<T>> activate(UUID id) throws AuthHubException;

    ResponseEntity<PaginatedResponse<T>> getAll(Pageable pageable) throws AuthHubException;

    ResponseEntity<DataResponse<T>> getById(UUID id) throws AuthHubException;
}
