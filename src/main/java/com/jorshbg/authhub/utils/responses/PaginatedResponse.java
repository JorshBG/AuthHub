package com.jorshbg.authhub.utils.responses;

import org.hibernate.boot.Metadata;

public record PaginatedResponse<T>(T[] data, Metadata metadata) {}

