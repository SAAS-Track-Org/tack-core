package com.example.trackingcore.domain.exception;

public class NotFoundException extends DomainException {

    public NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException of(final String entity, final Object id) {
        return new NotFoundException("%s with id '%s' was not found".formatted(entity, id));
    }

    public static NotFoundException of(final String entity) {
        return new NotFoundException("%s was not found".formatted(entity));
    }
}

