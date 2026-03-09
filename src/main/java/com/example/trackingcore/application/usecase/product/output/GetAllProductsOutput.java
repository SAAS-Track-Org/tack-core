package com.example.trackingcore.application.usecase.product.output;

import java.math.BigDecimal;
import java.util.UUID;

public record GetAllProductsOutput(
        UUID id,
        String name,
        String description,
        BigDecimal price
) {}

