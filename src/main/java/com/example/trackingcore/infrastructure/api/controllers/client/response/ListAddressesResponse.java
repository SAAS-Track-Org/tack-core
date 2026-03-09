package com.example.trackingcore.infrastructure.api.controllers.client.response;

import java.util.UUID;

public record ListAddressesResponse(
        UUID id,
        String zipCode,
        String street
) {}

