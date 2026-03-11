package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.AddressStatus;
import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ClientTrackResponse(
        String orderCode,
        String clientName,
        String clientPhone,
        OrderStatus deliveryStatus,
        AddressStatus addressStatus,
        AddressDetail address,
        PaymentMethod paymentMethod,
        List<PaymentMethod> availablePaymentMethods,
        BigDecimal totalAmount,
        String notes,
        DeliveryInfo delivery
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

    public record DeliveryInfo(
            UUID deliveryId,
            UUID publicCodeClient,
            DeliveryStatus status,
            BigDecimal currentLat,
            BigDecimal currentLng
    ) {}
}

