package com.example.trackingcore.infrastructure.persistence.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    Optional<DeliveryEntity> findByPublicCodeClient(UUID publicCodeClient);

    Optional<DeliveryEntity> findByPublicCodeDeliveryman(UUID publicCodeDeliveryman);

    List<DeliveryEntity> findAllByAppUser_Id(UUID appUserId);

    Optional<DeliveryEntity> findByIdAndAppUser_Id(UUID id, UUID appUserId);
}
