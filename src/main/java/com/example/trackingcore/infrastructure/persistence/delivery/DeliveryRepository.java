package com.example.trackingcore.infrastructure.persistence.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    Optional<DeliveryEntity> findByPublicCodeClient(UUID publicCodeClient);

    Optional<DeliveryEntity> findByPublicCodeDeliveryman(UUID publicCodeDeliveryman);
}
