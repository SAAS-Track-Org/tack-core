package com.example.trackingcore.application.usecase.delivery.output;

import java.math.BigDecimal;

public record UpdateLocationOutput(
        BigDecimal lat,
        BigDecimal lng
) {}

