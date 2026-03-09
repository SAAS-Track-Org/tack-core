package com.example.trackingcore.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int quantity,
        BigDecimal totalPrice
) {
}

