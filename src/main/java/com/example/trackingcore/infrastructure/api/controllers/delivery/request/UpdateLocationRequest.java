package com.example.trackingcore.infrastructure.api.controllers.delivery.request;

import java.math.BigDecimal;

public record UpdateLocationRequest(
        BigDecimal lat,
        BigDecimal lng
) {}

