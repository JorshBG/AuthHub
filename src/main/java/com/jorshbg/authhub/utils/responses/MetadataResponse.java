package com.jorshbg.authhub.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Metadata response class. This class must have methods to return class objects.
 */
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataResponse {
    /**
     * DateTime of the response, this attribute must be included in all methods
     */
    private LocalDateTime timestamp;

    //region Paginated variables
    /**
     * Page number
     */
    private Integer page;

    /**
     * Total items in the slice
     */
    private Integer itemsPerPage;

    /**
     * Total items in the database
     */
    private Long totalItems;

    /**
     * Total pages
     */
    private Integer totalPages;

    /**
     * Current items in the slice
     */
    private Integer currentItems;

    /**
     * Exists next page
     */
    private Boolean hasNext;

    /**
     * Exists previous page
     */
    private Boolean hasPrevious;

    //endregion

    /**
     * Default constructor for only timestamp
     * @param timestamp DateTime of the response
     */
    public MetadataResponse(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    /**
     * Constructor for paginated responses
     * @param timestamp DateTime of the response
     * @param page Page number
     * @param itemsPerPage Slice size
     * @param totalItems total items in the database
     * @param totalPages Total pages
     * @param currentItems Current items in the slice
     * @param hasNext It has next page
     * @param hasPrevious It has previous page
     */
    public MetadataResponse(LocalDateTime timestamp, int page, int itemsPerPage, long totalItems, int totalPages, int currentItems, boolean hasNext, boolean hasPrevious) {
        this.timestamp = timestamp;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentItems = currentItems;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    /**
     * Default metadata response to {@link DataResponse}
     * @return {@link MetadataResponse}
     */
    public static MetadataResponse defaultResponse() {
        return new MetadataResponse(LocalDateTime.now(ZoneId.systemDefault()));
    }

    /**
     * Default paginated metadata response to {@link PaginatedResponse}
     * @param page Page number
     * @param itemsPerPage Slice size
     * @param currentItems Current items in the slice
     * @param totalItems total items in the database
     * @param hasNext It has next page
     * @param hasPrevious It has previous page
     * @param totalPages Total pages
     * @return {@link MetadataResponse}
     */
    public static MetadataResponse paginatedMetadataResponse(final int page, final int itemsPerPage, final int currentItems, final long totalItems, final boolean hasNext, final boolean hasPrevious, final int totalPages) {
        return new MetadataResponse(LocalDateTime.now(ZoneId.systemDefault()), page, itemsPerPage, totalItems, totalPages, currentItems, hasNext, hasPrevious);
    }

}
