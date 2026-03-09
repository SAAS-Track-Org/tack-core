package com.example.trackingcore.infrastructure.api.controllers.order.response;

import java.util.UUID;

public record StandbyOrderResponse(
        String code,
        String clientName,
        UUID deliveryId,
        String label
) {}

