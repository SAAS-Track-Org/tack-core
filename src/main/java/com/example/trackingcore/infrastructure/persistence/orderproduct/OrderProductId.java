package com.example.trackingcore.infrastructure.persistence.orderproduct;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderProductId implements Serializable {

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;
}

