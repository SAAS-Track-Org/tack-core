package com.example.trackingcore.infrastructure.api.controllers.client.response;

import java.util.UUID;

public record SearchClientResponse(
        UUID id,
        String dataClient,
        String phoneNumber,
        String name
) {}

