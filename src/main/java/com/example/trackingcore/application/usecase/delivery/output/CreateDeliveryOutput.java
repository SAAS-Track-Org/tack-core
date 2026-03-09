package com.example.trackingcore.application.usecase.delivery.output;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.util.List;
import java.util.UUID;

public record CreateDeliveryOutput(
        UUID deliveryId,
        UUID publicCodeClient,
        UUID publicCodeDeliveryman,
        DeliveryStatus status,
        List<OrderOutput> orders
) {
    public record OrderOutput(
            String code,
            String clientName,
            AddressStatus status,
            AddressOutput address
    ) {}

    public record AddressOutput(
            UUID id,
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


