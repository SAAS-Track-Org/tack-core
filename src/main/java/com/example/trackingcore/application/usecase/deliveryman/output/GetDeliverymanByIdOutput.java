package com.example.trackingcore.application.usecase.deliveryman.output;

import java.util.UUID;

public record GetDeliverymanByIdOutput(
        UUID id,
        String name,
        String phoneNumber
) {}

