package com.example.trackingcore.infrastructure.api.controllers.deliveryman.response;

import java.util.UUID;

public record SearchDeliverymanResponse(
        UUID id,
        String deliveryMenName,
        String dataDeliveryMen
) {
}

