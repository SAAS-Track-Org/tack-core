package com.example.trackingcore.application.usecase.delivery.input;

import com.example.trackingcore.domain.model.enums.OrderStatus;

import java.util.UUID;

public record UpdateOrderStatusInput(
        UUID deliveryId,
        String orderCode,
        OrderStatus deliveryStatus
) {}

