package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import java.math.BigDecimal;

public record UpdateLocationResponse(
        BigDecimal lat,
        BigDecimal lng
) {}

