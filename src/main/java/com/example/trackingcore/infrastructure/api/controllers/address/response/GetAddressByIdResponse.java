package com.example.trackingcore.infrastructure.api.controllers.address.response;

import java.util.UUID;

public record GetAddressByIdResponse(
        UUID id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country
) {}

