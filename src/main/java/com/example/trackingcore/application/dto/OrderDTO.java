package com.example.trackingcore.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        ClientDTO client,
        List<ProductDTO> products,
        String notes,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
