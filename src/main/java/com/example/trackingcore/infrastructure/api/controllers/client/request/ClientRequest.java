package com.example.trackingcore.infrastructure.api.controllers.client.request;

import java.util.UUID;

public record ClientRequest(
        UUID id,
        String name,
        String phoneNumber,
        AddressRequest address
) {
}
