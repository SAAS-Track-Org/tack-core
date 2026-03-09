package com.example.trackingcore.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientDTO(
        UUID id,
        String name,
        String phoneNumber,
        AddressDTO address,
        LocalDateTime createdAt
) {
}

