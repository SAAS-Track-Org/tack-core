package com.example.trackingcore.application.usecase.delivery.input;

import java.util.UUID;

public record ClientTrackInput(
        UUID publicCodeClient,
        String orderCode
) {}

