package com.jorshbg.authhub.utils.responses;

import org.hibernate.boot.Metadata;

/**
 * Paginated response record to response to the client
 * @param data Array data
 * @param metadata Information about paginated object
 * @param <T> Type of the array data
 */
public record PaginatedResponse<T>(T[] data, Metadata metadata) {}

