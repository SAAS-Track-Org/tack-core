package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import java.math.BigDecimal;

public record ProductResponse(
        String name,
        BigDecimal price
) {}
