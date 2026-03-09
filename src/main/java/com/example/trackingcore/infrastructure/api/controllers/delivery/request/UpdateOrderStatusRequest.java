package com.example.trackingcore.infrastructure.api.controllers.delivery.request;

import com.example.trackingcore.domain.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull OrderStatus deliveryStatus
) {}

