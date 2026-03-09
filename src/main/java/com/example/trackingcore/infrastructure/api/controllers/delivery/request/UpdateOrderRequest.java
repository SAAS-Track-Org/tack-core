package com.example.trackingcore.infrastructure.api.controllers.delivery.request;

import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;

public record UpdateOrderRequest(
        String clientName,
        String clientPhone,
        String notes,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        AddressDetail address
) {
    public record AddressDetail(
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

