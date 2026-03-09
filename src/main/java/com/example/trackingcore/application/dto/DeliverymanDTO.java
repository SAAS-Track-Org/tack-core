package com.example.trackingcore.application.dto;

import java.util.UUID;

public record DeliverymanDTO(
        UUID id,
        String name,
        String phoneNumber
) {
}

