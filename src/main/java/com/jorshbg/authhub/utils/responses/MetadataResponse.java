package com.jorshbg.authhub.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataResponse {
    LocalDateTime timestamp;

    public static MetadataResponse defaultResponse() {
        return new MetadataResponse(LocalDateTime.now(ZoneId.systemDefault()));
    }
}
