package com.jorshbg.authhub.utils.responses;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthHubResponse {

    public static <T> ResponseEntity<PaginatedResponse<T>> paginated(Page<T> page, HttpStatus status){
        var response = new PaginatedResponse<>(page.getContent(), MetadataResponse.paginatedMetadataResponse(
                page.getNumber(),
                page.getNumberOfElements(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                page.getTotalPages()
        ));
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<PaginatedResponse<T>> paginated(Page<T> page){
        return paginated(page, HttpStatus.OK);
    }

    public static <T> ResponseEntity<DataResponse<T>> data(T data, HttpStatus status, MetadataResponse metadataResponse){
        return new ResponseEntity<>(
                new DataResponse<>(data, metadataResponse),
                status
        );
    }

    public static <T> ResponseEntity<DataResponse<T>> data(T data, MetadataResponse metadataResponse){
        return new ResponseEntity<>(
                new DataResponse<>(data, metadataResponse),
                HttpStatus.OK
        );
    }

    public static <T> ResponseEntity<DataResponse<T>> data(T data, HttpStatus status){
        return new ResponseEntity<>(
                new DataResponse<>(data, MetadataResponse.defaultResponse()),
                status
        );
    }

    public static <T> ResponseEntity<DataResponse<T>> data(T data){
        return new ResponseEntity<>(
                new DataResponse<>(data, MetadataResponse.defaultResponse()),
                HttpStatus.OK
        );
    }

}
