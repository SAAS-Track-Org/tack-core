package com.example.trackingcore.infrastructure.api.controllers.client.request;

import java.util.UUID;

public record AddressRequest(
        UUID id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country
) {
}

