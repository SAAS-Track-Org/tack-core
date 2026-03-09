package com.example.trackingcore.application.usecase.order.output;

import java.util.UUID;

public record StandbyOrderSummaryOutput(
        String code,
        String clientName,
        UUID deliveryId,
        String label
) {}

