package com.jorshbg.authhub.utils.responses;

import java.util.List;

/**
 * Paginated response record to response to the client
 * @param data Array data
 * @param metadata Information about paginated object
 * @param <T> Type of the array data
 */
public record PaginatedResponse<T>(List<T> data, MetadataResponse metadata) {}

