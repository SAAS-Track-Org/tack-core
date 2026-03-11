package com.example.trackingcore.domain.model;

import com.example.trackingcore.domain.exception.InvalidDeliveryStateException;
import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Delivery {
    private final UUID id;
    private final UUID appUserId;
    private final UUID publicCodeClient;
    private final UUID publicCodeDeliveryman;
    private DeliveryStatus status;
    private final Deliveryman deliveryman;
    private List<Order> orders;
    private final BigDecimal currentLat;
    private final BigDecimal currentLng;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deliveredAt;

    private Delivery(
            UUID id,
            UUID appUserId,
            UUID publicCodeClient,
            UUID publicCodeDeliveryman,
            DeliveryStatus status,
            Deliveryman deliveryman, List<Order> orders,
            BigDecimal currentLat,
            BigDecimal currentLng,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deliveredAt
    ) {
        this.orders = orders;
        this.id = id;
        this.appUserId = appUserId;
        this.publicCodeClient = publicCodeClient;
        this.publicCodeDeliveryman = publicCodeDeliveryman;
        this.status = status;
        this.deliveryman = deliveryman;
        this.currentLat = currentLat;
        this.currentLng = currentLng;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deliveredAt = deliveredAt;

        this.validate();
    }

    public static Delivery create(
            UUID appUserId,
            Deliveryman deliveryman,
            List<Order> orders
    ) {
        final var id = UUID.randomUUID();
        final var publicCodeClient = UUID.randomUUID();
        final var publicCodeDeliveryman = UUID.randomUUID();

        return new Delivery(
                id,
                appUserId,
                publicCodeClient,
                publicCodeDeliveryman,
                DeliveryStatus.CREATED,
                deliveryman,
                orders,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
    }

    public static Delivery with(
            UUID id,
            UUID appUserId,
            UUID publicCodeClient,
            UUID publicCodeDeliveryman,
            DeliveryStatus status,
            Deliveryman deliveryman,
            List<Order> orders,
            BigDecimal currentLat,
            BigDecimal currentLng,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deliveredAt
    ) {
        return new Delivery(
                id,
                appUserId,
                publicCodeClient,
                publicCodeDeliveryman,
                status,
                deliveryman,
                orders,
                currentLat,
                currentLng,
                createdAt,
                updatedAt,
                deliveredAt
        );
    }

    private void validate() {
        if (deliveryman == null) {
            throw new IllegalArgumentException("Deliveryman cannot be null");
        }
        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("Delivery must have at least one order");
        }
    }

    public boolean canBeCancelled() {
        return status == DeliveryStatus.CREATED || status == DeliveryStatus.IN_TRANSIT;
    }

    /** Returns true when this delivery should transition CREATED → IN_TRANSIT. */
    public boolean shouldInitiate(final OrderStatus incomingOrderStatus) {
        return this.status == DeliveryStatus.CREATED && incomingOrderStatus != OrderStatus.WAITING;
    }

    /**
     * Returns true when every active order (excluding STANDBY/DELETED) is in a terminal
     * state, meaning the delivery can transition IN_TRANSIT → DELIVERED.
     * Uses the orders already loaded in this aggregate — no extra query needed.
     */
    public boolean isFullyCompleted() {
        if (this.status != DeliveryStatus.IN_TRANSIT) return false;
        final var active = this.orders.stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.STANDBY
                          && o.getDeliveryStatus() != OrderStatus.DELETED)
                .toList();
        return !active.isEmpty() && active.stream()
                .allMatch(o -> o.getDeliveryStatus() == OrderStatus.DELIVERED
                            || o.getDeliveryStatus() == OrderStatus.DELETED);
    }


    public void syncOrder(final Order updated) {
        this.orders = this.orders.stream()
                .map(o -> o.getCode().equals(updated.getCode()) ? updated : o)
                .toList();
    }

    public void initiateDelivery() {
        if (this.status != DeliveryStatus.CREATED) {
            throw new IllegalStateException(
                    "Cannot initiate delivery with status: " + this.status
            );
        }
        this.status = DeliveryStatus.IN_TRANSIT;
    }

    public void completeDelivery() {
        if (status != DeliveryStatus.IN_TRANSIT) {
            throw new IllegalStateException(
                    "Cannot complete delivery with status: " + this.status
            );
        }
        this.status = DeliveryStatus.DELIVERED;
    }

    public Delivery updateLocation(final BigDecimal lat, final BigDecimal lng) {
        if (status != DeliveryStatus.CREATED && this.status != DeliveryStatus.IN_TRANSIT) {
            throw new InvalidDeliveryStateException(
                    "Cannot update location for delivery with status: " + this.status
            );
        }
        return new Delivery(
                id, appUserId, publicCodeClient, publicCodeDeliveryman,
                status,
                deliveryman, orders,
                lat, lng,
                createdAt, LocalDateTime.now(), deliveredAt
        );
    }
}
