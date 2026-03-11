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
    private final String establishmentName;
    private final String address;
    private final LocalDateTime createdAt;

    public static AppUser create(final String email) {
        return new AppUser(UUID.randomUUID(), email, List.of(), null, null, LocalDateTime.now());
    }

    public static AppUser with(
            final UUID id,
            final String email,
            final List<PaymentMethod> paymentMethods,
            final String establishmentName,
            final String address,
            final LocalDateTime createdAt
    ) {
        return new AppUser(id, email, paymentMethods, establishmentName, address, createdAt);
    }

    public AppUser withPaymentMethods(final List<PaymentMethod> newPaymentMethods) {
        return new AppUser(this.id, this.email, newPaymentMethods, this.establishmentName, this.address, this.createdAt);
    }

    public AppUser updateProfile(
            final List<PaymentMethod> newPaymentMethods,
            final String newEstablishmentName,
            final String newAddress
    ) {
        return new AppUser(this.id, this.email, newPaymentMethods, newEstablishmentName, newAddress, this.createdAt);
    }

    private AppUser(
            final UUID id,
            final String email,
            final List<PaymentMethod> paymentMethods,
            final String establishmentName,
            final String address,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.email = email;
        this.paymentMethods = paymentMethods;
        this.establishmentName = establishmentName;
        this.address = address;
        this.createdAt = createdAt;
    }
}
