package com.example.trackingcore.application.usecase.delivery.input;

import java.util.UUID;

public record GetDeliveryDetailInput(UUID deliveryId, UUID appUserId) {
}

