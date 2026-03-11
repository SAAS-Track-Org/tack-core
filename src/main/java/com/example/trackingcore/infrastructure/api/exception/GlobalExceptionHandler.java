package com.example.trackingcore.infrastructure.api.exception;

import com.example.trackingcore.domain.exception.BusinessException;
import com.example.trackingcore.domain.exception.DomainException;
import com.example.trackingcore.domain.exception.InvalidDeliveryStateException;
import com.example.trackingcore.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -------------------------------------------------------------------------
    // 404 - Not Found
    // -------------------------------------------------------------------------

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(
            final NotFoundException ex,
            final HttpServletRequest request
    ) {
        log.warn("NotFoundException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -------------------------------------------------------------------------
    // 409 - Conflict (invalid delivery state transition)
    // -------------------------------------------------------------------------

    @ExceptionHandler(InvalidDeliveryStateException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDeliveryStateException(
            final InvalidDeliveryStateException ex,
            final HttpServletRequest request
    ) {
        log.warn("InvalidDeliveryStateException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // -------------------------------------------------------------------------
    // 422 - Business Rule Violation
    // -------------------------------------------------------------------------

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(
            final BusinessException ex,
            final HttpServletRequest request
    ) {
        log.warn("BusinessException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Unprocessable Entity",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    // -------------------------------------------------------------------------
    // 400 - Validation Errors (Bean Validation)
    // -------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpServletRequest request
    ) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        final List<ApiErrorResponse.FieldError> fieldErrors = buildFieldErrors(ex.getBindingResult());
        final var body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Validation failed for one or more fields",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // -------------------------------------------------------------------------
    // 400 - Missing Required Request Parameter
    // -------------------------------------------------------------------------

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex,
            final HttpServletRequest request
    ) {
        log.warn("MissingServletRequestParameterException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Required parameter '%s' of type '%s' is missing".formatted(
                        ex.getParameterName(), ex.getParameterType()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // -------------------------------------------------------------------------
    // 400 - Type Mismatch (e.g., invalid UUID or enum value in path/param)
    // -------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex,
            final HttpServletRequest request
    ) {
        log.warn("MethodArgumentTypeMismatchException: {}", ex.getMessage());
        final String expectedType = ex.getRequiredType() != null
                ? ex.getRequiredType().getSimpleName()
                : "unknown";
        final var body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Parameter '%s' should be of type '%s'".formatted(ex.getName(), expectedType),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // -------------------------------------------------------------------------
    // 400 - Malformed JSON body
    // -------------------------------------------------------------------------

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpServletRequest request
    ) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Malformed or unreadable request body",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // -------------------------------------------------------------------------
    // 400 - Generic Domain Exception (fallback for DomainException)
    // -------------------------------------------------------------------------

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(
            final DomainException ex,
            final HttpServletRequest request
    ) {
        log.warn("DomainException: {}", ex.getMessage());
        final var body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // -------------------------------------------------------------------------
    // 500 - Unexpected Errors
    // -------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            final Exception ex,
            final HttpServletRequest request
    ) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        final var body = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private List<ApiErrorResponse.FieldError> buildFieldErrors(final BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fe -> new ApiErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
                .toList();
    }
}

