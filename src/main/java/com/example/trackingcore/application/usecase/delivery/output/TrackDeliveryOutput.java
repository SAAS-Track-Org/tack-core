package com.example.trackingcore.application.usecase.delivery.output;

import java.math.BigDecimal;

public record TrackDeliveryOutput(
        String status,
        String deliverymanName,
        BigDecimal currentLat,
        BigDecimal currentLng,
        OrderOutput order
) {
    public record OrderOutput(String orderCode) {}
}
