package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.util.List;
import java.util.UUID;

public record CreateDeliveryResponse(
        UUID deliveryId,
        UUID publicCodeClient,
        UUID publicCodeDeliveryman,
        DeliveryStatus status,
        List<OrderResponse> orders
) {
    public record OrderResponse(
            String code,
            String clientName,
            Address address,
            AddressStatus status
    ) {
    }

    public record Address(
            UUID id,
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {
    }
}
