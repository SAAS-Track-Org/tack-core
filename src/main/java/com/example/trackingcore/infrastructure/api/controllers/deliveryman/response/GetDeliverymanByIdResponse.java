package com.example.trackingcore.infrastructure.api.controllers.deliveryman.response;

import java.util.UUID;

public record GetDeliverymanByIdResponse(
        UUID id,
        String name,
        String phoneNumber
) {}

