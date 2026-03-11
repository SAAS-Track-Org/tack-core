package com.example.trackingcore.domain.model;

import com.example.trackingcore.domain.model.enums.AddressStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id;
    private final UUID deliveryId;
    private final String code;
    private final Client client;
    private final Address deliveryAddress;
    private final String notes;
    private final AddressStatus status;
    private final OrderStatus deliveryStatus;
    private final BigDecimal totalAmount;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static Order create(Client client, String code) {
        final var id = UUID.randomUUID();
        final var now = LocalDateTime.now();
        return new Order(id, null, code, client, null, null, AddressStatus.ADDRESS_PENDING,
                OrderStatus.WAITING, null, null, now, now);
    }

    public static Order with(
            UUID id, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new Order(id, null, code, client, deliveryAddress, notes, status, deliveryStatus,
                totalAmount, paymentMethod, createdAt, updatedAt);
    }

    public static Order with(
            UUID id,
            UUID deliveryId,
            String code,
            Client client,
            Address deliveryAddress,
            String notes,
            AddressStatus status,
            OrderStatus deliveryStatus,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Order(
                id,
                deliveryId,
                code,
                client,
                deliveryAddress,
                notes,
                status,
                deliveryStatus,
                totalAmount,
                paymentMethod,
                createdAt,
                updatedAt
        );
    }

    private Order(
            UUID id, UUID deliveryId, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.id = id;
        this.deliveryId = deliveryId;
        this.code = code;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.status = status;
        this.deliveryStatus = deliveryStatus;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validate();
    }

    public Order relinkToDelivery(final UUID newDeliveryId) {
        return new Order(this.id, newDeliveryId, this.code, this.client, this.deliveryAddress,
                this.notes, this.status, OrderStatus.WAITING, this.totalAmount, this.paymentMethod,
                this.createdAt, LocalDateTime.now());
    }

    public Order withClientName(final String newClientName) {
        final var updatedClient = this.client != null
                ? this.client.withName(newClientName)
                : Client.create(newClientName, null);
        return new Order(this.id, this.deliveryId, this.code, updatedClient, this.deliveryAddress,
                this.notes, this.status, this.deliveryStatus, this.totalAmount, this.paymentMethod,
                this.createdAt, LocalDateTime.now());
    }

    /**
     * Updates the order's address / payment data AND client contact data in one step.
     * Any null argument falls back to the current value.
     */
    public Order updateClientTrackData(
            final String clientName,
            final String clientPhone,
            final com.example.trackingcore.domain.model.enums.PaymentMethod paymentMethod,
            final Address deliveryAddress
    ) {
        final var newStatus = deliveryAddress != null
                ? AddressStatus.ADDRESS_CONFIRMED
                : this.status;

        final var updatedClient = this.client != null
                ? this.client.withContact(
                        clientName != null && !clientName.isBlank() ? clientName : this.client.getName(),
                        clientPhone != null && !clientPhone.isBlank() ? clientPhone : this.client.getPhoneNumber()
                  )
                : Client.create(
                        clientName != null ? clientName : "",
                        clientPhone
                  );

        return new Order(
                this.id, this.deliveryId, this.code, updatedClient,
                deliveryAddress != null ? deliveryAddress : this.deliveryAddress,
                this.notes, newStatus, this.deliveryStatus,
                this.totalAmount, paymentMethod,
                this.createdAt, LocalDateTime.now()
        );
    }

    public Order update(
            String notes,
            BigDecimal totalAmount,
            PaymentMethod paymentMethod,
            Address deliveryAddress
    ) {
        final var newStatus = deliveryAddress != null
                ? AddressStatus.ADDRESS_CONFIRMED
                : this.status;
        return new Order(this.id, this.deliveryId, this.code, this.client,
                deliveryAddress != null ? deliveryAddress : this.deliveryAddress,
                notes, newStatus, this.deliveryStatus, totalAmount, paymentMethod,
                this.createdAt, LocalDateTime.now());
    }

    public Order updateDeliveryStatus(final OrderStatus newDeliveryStatus) {
        validateTransition(this.deliveryStatus, newDeliveryStatus);
        return new Order(this.id, this.deliveryId, this.code, this.client, this.deliveryAddress,
                this.notes, this.status, newDeliveryStatus, this.totalAmount, this.paymentMethod,
                this.createdAt, LocalDateTime.now());
    }

    private static void validateTransition(OrderStatus current, OrderStatus next) {
        if (next == OrderStatus.DELETED || next == OrderStatus.STANDBY) return;
        final boolean valid = switch (current) {
            case WAITING -> next == OrderStatus.ON_THE_WAY;
            case ON_THE_WAY -> next == OrderStatus.ARRIVING;
            case ARRIVING -> next == OrderStatus.DELIVERED;
            case DELIVERED, STANDBY, DELETED -> false;
        };
        if (!valid) {
            throw new IllegalArgumentException(
                    "Invalid delivery status transition: " + current + " → " + next);
        }
    }

    private void validate() {
        if (client == null) {
            throw new IllegalArgumentException("Order client cannot be null");
        }
    }
}