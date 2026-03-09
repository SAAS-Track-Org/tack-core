package com.example.trackingcore.application.dto;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DeliveryDTO(
        UUID id,
        UUID publicCodeClient,
        UUID publicCodeDeliveryman,
        DeliveryStatus status,
        DeliverymanDTO deliveryman,
        List<OrderDTO> orders,
        BigDecimal currentLat,
        BigDecimal currentLng,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deliveredAt
) {
}

