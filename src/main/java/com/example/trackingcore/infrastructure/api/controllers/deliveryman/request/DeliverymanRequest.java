package com.example.trackingcore.infrastructure.api.controllers.deliveryman.request;

import java.util.UUID;

public record DeliverymanRequest(
        UUID id,
        String name,
        String phoneNumber
) { }
