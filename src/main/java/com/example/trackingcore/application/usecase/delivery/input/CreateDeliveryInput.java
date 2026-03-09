package com.example.trackingcore.application.usecase.delivery.input;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateDeliveryInput(
        DeliverymanInput deliveryman,
        List<OrderInput> orders
) {

    public record DeliverymanInput(
            UUID id,
            String name,
            String phoneNumber
    ) {
        public boolean isExisting() {
            return id != null;
        }
    }

    public record OrderInput(
            ClientInput client,
            String notes,
            List<ProductInput> products
    ) {
    }

    public record ClientInput(
            UUID id,
            String name,
            String phoneNumber,
            AddressInput address
    ) {
        public boolean isExisting() {
            return id != null;
        }
    }

    public record AddressInput(
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
        public boolean isExisting() {
            return id != null;
        }
    }

    public record ProductInput(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            int quantity
    ) {
        public boolean isExisting() {
            return id != null;
        }
    }
}