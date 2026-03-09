package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DeliveryDetailResponse(
        UUID deliveryId,
        UUID publicCodeDeliveryman,
        UUID publicCodeClient,
        DeliveryStatus status,
        String deliverymanName,
        String deliverymanPhone,
        String createdAt,
        List<OrderDetail> orders
) {
    public record OrderDetail(
            String code,
            String clientName,
            String clientPhone,
            AddressStatus addressStatus,
            OrderStatus deliveryStatus,
            String notes,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            AddressDetail address
    ) {}

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
