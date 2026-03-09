package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.util.List;
import java.util.UUID;

public record DeliverySummaryResponse(
        UUID deliveryId,
        DeliveryStatus status,
        List<Order> orders,
        String clientName,
        String deliverymanName,
        String createdAt
) {
    public record Order(
            String code,
            AddressStatus stausAddress,
            OrderStatus deliveryStatus
    ) {}
}
