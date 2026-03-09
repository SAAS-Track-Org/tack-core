package com.example.trackingcore.infrastructure.persistence.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, OrderProductId> {

    List<OrderProductEntity> findByIdOrderId(UUID orderId);

    List<OrderProductEntity> findByIdProductId(UUID productId);
}

