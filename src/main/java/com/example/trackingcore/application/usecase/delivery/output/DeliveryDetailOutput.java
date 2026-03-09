package com.example.trackingcore.application.usecase.delivery.output;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DeliveryDetailOutput(
        UUID deliveryId,
        UUID publicCodeDeliveryman,
        UUID publicCodeClient,
        DeliveryStatus status,
        String deliverymanName,
        String deliverymanPhone,
        LocalDateTime createdAt,
        List<OrderDetailOutput> orders
) {
    public record OrderDetailOutput(
            String code,
            String clientName,
            String clientPhone,
            AddressStatus addressStatus,
            OrderStatus deliveryStatus,
            String notes,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            AddressDetailOutput address
    ) {}

    public record AddressDetailOutput(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {}
}
