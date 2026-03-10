package com.example.trackingcore.infrastructure.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        List<FieldError> errors
) {

    public record FieldError(String field, String message) {}

    public static ApiErrorResponse of(
            final int status,
            final String error,
            final String message,
            final String path
    ) {
        return new ApiErrorResponse(status, error, message, path, LocalDateTime.now(), null);
    }

    public static ApiErrorResponse of(
            final int status,
            final String error,
            final String message,
            final String path,
            final List<FieldError> errors
    ) {
        return new ApiErrorResponse(status, error, message, path, LocalDateTime.now(), errors);
    }
}

