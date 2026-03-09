package com.example.trackingcore.application.usecase.client.output;

import java.util.UUID;

public record SearchClientOutput(
        UUID id,
        String dataClient,
        String phoneNumber,
        String name
) {}

