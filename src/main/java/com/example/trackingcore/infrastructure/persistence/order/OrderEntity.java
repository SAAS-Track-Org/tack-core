package com.example.trackingcore.infrastructure.persistence.order;

import com.example.trackingcore.infrastructure.persistence.address.AddressEntity;
import com.example.trackingcore.infrastructure.persistence.client.ClientEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    private String code;

    @Column(name = "delivery_id", insertable = false, updatable = false)
    private UUID deliveryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = true)
    private AddressEntity deliveryAddress;

    @Column(nullable = false)
    private String addressStatus;

    @Column(nullable = false)
    private String statusOrder;

    private String notes;

    private BigDecimal totalAmount;

    private String paymentMethod;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
