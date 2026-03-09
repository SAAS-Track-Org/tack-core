package com.example.trackingcore.application.usecase.delivery.input;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateLocationInput(
        UUID publicCodeDeliveryman,
        BigDecimal lat,
        BigDecimal lng
) {}

