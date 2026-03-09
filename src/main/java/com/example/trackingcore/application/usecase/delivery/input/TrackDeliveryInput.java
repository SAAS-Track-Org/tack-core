package com.example.trackingcore.application.usecase.delivery.input;

import java.util.UUID;

public record TrackDeliveryInput(
        UUID publicCodeClient,
        String orderCode
) {}

