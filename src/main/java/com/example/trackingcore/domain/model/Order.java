package com.example.trackingcore.domain.model;

import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import com.example.trackingcore.domain.model.enums.AddressStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final List<Product> products;

    public static Order create(Client client, String code) {
        final var id = UUID.randomUUID();
        final var now = LocalDateTime.now();
        return new Order(id, null, code, client, null, null, AddressStatus.ADDRESS_PENDING,
                OrderStatus.WAITING, null, null, now, now, new ArrayList<>());
    }

    public static Order with(
            UUID id, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new Order(id, null, code, client, deliveryAddress, notes, status, deliveryStatus,
                totalAmount, paymentMethod, createdAt, updatedAt, new ArrayList<>());
    }

    public static Order with(
            UUID id, UUID deliveryId, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new Order(id, deliveryId, code, client, deliveryAddress, notes, status, deliveryStatus,
                totalAmount, paymentMethod, createdAt, updatedAt, new ArrayList<>());
    }

    public static Order with(
            UUID id, UUID deliveryId, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt, List<Product> products
    ) {
        return new Order(id, deliveryId, code, client, deliveryAddress, notes, status, deliveryStatus,
                totalAmount, paymentMethod, createdAt, updatedAt, products);
    }

    private Order(
            UUID id, UUID deliveryId, String code, Client client, Address deliveryAddress,
            String notes, AddressStatus status, OrderStatus deliveryStatus,
            BigDecimal totalAmount, PaymentMethod paymentMethod,
            LocalDateTime createdAt, LocalDateTime updatedAt, List<Product> products
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
        this.products = products != null ? products : new ArrayList<>();
        this.validate();
    }

    public Order relinkToDelivery(final UUID newDeliveryId) {
        return new Order(this.id, newDeliveryId, this.code, this.client, this.deliveryAddress,
                this.notes, this.status, OrderStatus.WAITING, this.totalAmount, this.paymentMethod,
                this.createdAt, LocalDateTime.now(), this.products);
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
                this.createdAt, LocalDateTime.now(), this.products);
    }

    public Order updateDeliveryStatus(final OrderStatus newDeliveryStatus) {
        validateTransition(this.deliveryStatus, newDeliveryStatus);
        return new Order(this.id, this.deliveryId, this.code, this.client, this.deliveryAddress,
                this.notes, this.status, newDeliveryStatus, this.totalAmount, this.paymentMethod,
                this.createdAt, LocalDateTime.now(), this.products);
    }

    private static void validateTransition(OrderStatus current, OrderStatus next) {
        if (next == OrderStatus.DELETED || next == OrderStatus.STANDBY) return;
        final boolean valid = switch (current) {
            case WAITING    -> next == OrderStatus.ON_THE_WAY;
            case ON_THE_WAY -> next == OrderStatus.ARRIVING;
            case ARRIVING   -> next == OrderStatus.DELIVERED;
            case DELIVERED, STANDBY, DELETED -> false;
        };
        if (!valid) {
            throw new IllegalArgumentException(
                    "Invalid delivery status transition: " + current + " → " + next);
        }
    }

    public BigDecimal computedTotalAmount() {
        if (totalAmount != null) return totalAmount;
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validate() {
        if (client == null) {
            throw new IllegalArgumentException("Order client cannot be null");
        }
    }
}