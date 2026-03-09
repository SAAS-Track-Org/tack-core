package com.example.trackingcore.application.usecase.delivery.output;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetAllDeliveriesOutput(
        UUID deliveryId,
        DeliveryStatus status,
        List<OrderOutput> orders,
        String clientName,
        String deliverymanName,
        LocalDateTime createdAt
) {
    public record OrderOutput(
            String code,
            AddressStatus addressStatus,
            OrderStatus deliveryStatus
    ) {}
}
