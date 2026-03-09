package com.example.trackingcore.application.usecase.address.output;

import java.util.UUID;

public record GetAddressByIdOutput(
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

