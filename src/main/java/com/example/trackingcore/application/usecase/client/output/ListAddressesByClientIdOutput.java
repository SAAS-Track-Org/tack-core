package com.example.trackingcore.application.usecase.client.output;

import java.util.UUID;

public record ListAddressesByClientIdOutput(
        UUID id,
        String zipCode,
        String street
) {}

