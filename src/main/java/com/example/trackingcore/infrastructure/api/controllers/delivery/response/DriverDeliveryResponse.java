package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.AddressStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DriverDeliveryResponse(
        UUID deliveryId,
        UUID publicCodeDeliveryman,
        String status,
        List<OrderResponse> orders
) {
    public record OrderResponse(
            String orderCode,
            String clientName,
            String clientPhone,
            OrderStatus deliveryStatus,
            AddressStatus addressStatus,
            String notes,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            AddressResponse address
    ) {}

    public record AddressResponse(
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
