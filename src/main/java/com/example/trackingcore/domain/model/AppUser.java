package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class AppUser {

    private final UUID id;
    private final String email;
    private final List<PaymentMethod> paymentMethods;
    private final LocalDateTime createdAt;

    public static AppUser create(final String email, final List<PaymentMethod> paymentMethods) {
        final var id = UUID.randomUUID();
        final var createdAt = LocalDateTime.now();
        return new AppUser(id, email, paymentMethods, createdAt);
    }

    public static AppUser with(
            final UUID id,
            final String email,
            final List<PaymentMethod> paymentMethods,
            final LocalDateTime createdAt
    ) {
        return new AppUser(id, email, paymentMethods, createdAt);
    }

    public AppUser withPaymentMethods(final List<PaymentMethod> newPaymentMethods) {
        return new AppUser(this.id, this.email, newPaymentMethods, this.createdAt);
    }

    private AppUser(final UUID id, final String email, final List<PaymentMethod> paymentMethods, final LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.paymentMethods = paymentMethods;
        this.createdAt = createdAt;
    }
}
