package com.example.trackingcore.application.usecase.delivery.output;

import com.example.trackingcore.domain.model.enums.AddressStatus;
import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record ClientTrackOutput(
        String orderCode,
        String clientName,
        String clientPhone,
        OrderStatus deliveryStatus,
        AddressStatus addressStatus,
        AddressOutput address,
        PaymentMethod paymentMethod,
        BigDecimal totalAmount,
        String notes,
        DeliveryInfo delivery
) {
    public record AddressOutput(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {}

    public record DeliveryInfo(
            UUID deliveryId,
            UUID publicCodeClient,
            DeliveryStatus status,
            BigDecimal currentLat,
            BigDecimal currentLng
    ) {}
}

