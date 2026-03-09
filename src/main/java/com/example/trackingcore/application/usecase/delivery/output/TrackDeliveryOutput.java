package com.example.trackingcore.application.usecase.delivery.output;

import java.math.BigDecimal;
import java.util.List;

public record TrackDeliveryOutput(
        String status,
        String deliverymanName,
        BigDecimal currentLat,
        BigDecimal currentLng,
        OrderOutput order
) {
    public record OrderOutput(
            String orderCode,
            BigDecimal totalAmount,
            List<ProductOutput> products
    ) {}

    public record ProductOutput(
            String name,
            BigDecimal price
    ) {}
}

