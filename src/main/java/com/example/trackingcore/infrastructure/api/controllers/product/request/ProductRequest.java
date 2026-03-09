package com.example.trackingcore.infrastructure.api.controllers.product.request;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int quantity
) {
}
