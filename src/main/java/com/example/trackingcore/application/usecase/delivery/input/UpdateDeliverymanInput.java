package com.example.trackingcore.application.usecase.delivery.input;

import java.util.UUID;

public record UpdateDeliverymanInput(
        UUID deliveryId,
        UUID appUserId,
        String name,
        String phoneNumber
) {}

