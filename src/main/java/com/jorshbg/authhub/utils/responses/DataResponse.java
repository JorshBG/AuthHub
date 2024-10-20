package com.jorshbg.authhub.utils.responses;

/**
 * Data response for only one entity
 * @param data Data response
 * @param metadata Metadata response
 * @param <T> Data type
 */
public record DataResponse<T>(
        T data,
        MetadataResponse metadata
) {}
