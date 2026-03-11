package com.example.trackingcore.infrastructure.api.controllers.delivery.request;

import com.example.trackingcore.domain.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;

public record UpdateClientTrackRequest(
        String clientName,
        String clientPhone,
        PaymentMethod paymentMethod,
        AddressRequest address
) {
    public record AddressRequest(
            @NotBlank String street,
            @NotBlank String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {}
}

