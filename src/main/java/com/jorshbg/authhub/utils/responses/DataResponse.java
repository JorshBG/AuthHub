package com.jorshbg.authhub.utils.responses;

public record DataResponse<T>(
        T data,
        MetadataResponse metadata
) {}
