package com.example.trackingcore.infrastructure.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Query(value = "SELECT nextval('delivery.order_code_seq')", nativeQuery = true)
    Long nextCode();

    List<OrderEntity> findByDeliveryId(UUID deliveryId);

    @Query("SELECT o FROM OrderEntity o WHERE o.deliveryId = ?1 AND o.statusOrder NOT IN ?2")
    List<OrderEntity> findByDeliveryIdAndStatusOrderNotIn(UUID deliveryId, List<String> excludedStatuses);

    @Query("SELECT o FROM OrderEntity o WHERE o.statusOrder = 'STANDBY' ORDER BY o.createdAt DESC")
    List<OrderEntity> findAllStandby();

    Optional<OrderEntity> findByCode(String code);

    Optional<OrderEntity> findByDeliveryIdAndCode(UUID deliveryId, String code);
}
