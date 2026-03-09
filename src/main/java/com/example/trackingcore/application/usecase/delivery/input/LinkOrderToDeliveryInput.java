package com.example.trackingcore.application.usecase.delivery.input;

import java.util.UUID;

public record LinkOrderToDeliveryInput(
        UUID deliveryId,
        String orderCode
) {}

