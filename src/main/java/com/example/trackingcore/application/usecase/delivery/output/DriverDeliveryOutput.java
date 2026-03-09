package com.example.trackingcore.application.usecase.delivery.output;

import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DriverDeliveryOutput(
        UUID deliveryId,
        UUID publicCodeDeliveryman,
        String status,
        List<OrderOutput> orders
) {
    public record OrderOutput(
            String orderCode,
            String clientName,
            String clientPhone,
            OrderStatus deliveryStatus,
            String notes,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            AddressOutput address
    ) {}

    public record AddressOutput(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {}

    public record ProductOutput(
            String name,
            BigDecimal price
    ) {}
}
