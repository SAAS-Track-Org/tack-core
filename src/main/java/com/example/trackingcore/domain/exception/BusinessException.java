package com.example.trackingcore.domain.exception;

public class BusinessException extends DomainException {

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

