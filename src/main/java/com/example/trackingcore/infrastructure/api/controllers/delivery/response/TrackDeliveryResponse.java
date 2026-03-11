package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import java.math.BigDecimal;
import java.util.List;

public record TrackDeliveryResponse(
        String status,
        String deliverymanName,
        BigDecimal currentLat,
        BigDecimal currentLng,
        OrderResponse order
) {
    public record OrderResponse(
            String orderCode
    ) {}
}
