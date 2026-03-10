package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentMethod {

    private final String id;
    private final String label;
    private final LocalDateTime createdAt;

    public static PaymentMethod with(
            final String id,
            final String label,
            final LocalDateTime createdAt
    ) {
        return new PaymentMethod(id, label, createdAt);
    }

    private PaymentMethod(final String id, final String label, final LocalDateTime createdAt) {
        this.id = id;
        this.label = label;
        this.createdAt = createdAt;
    }
}

