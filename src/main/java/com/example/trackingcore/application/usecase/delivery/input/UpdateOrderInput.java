package com.example.trackingcore.application.usecase.delivery.input;

import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateOrderInput(
        UUID deliveryId,
        String orderCode,
        String clientName,
        String clientPhone,
        String notes,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        AddressInput address
) {
    public record AddressInput(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {}
}

