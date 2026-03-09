package com.example.trackingcore.application.usecase.deliveryman.output;

import java.util.UUID;

public record SearchDeliverymanOutput(
        UUID id,
        String dataDeliveryMen,
        String deliveryMenName
) {}

