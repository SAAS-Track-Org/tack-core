package com.example.trackingcore.infrastructure.persistence.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    Optional<DeliveryEntity> findByPublicCodeClient(UUID publicCodeClient);

    Optional<DeliveryEntity> findByPublicCodeDeliveryman(UUID publicCodeDeliveryman);

    List<DeliveryEntity> findAllByAppUser_Id(UUID appUserId);

    Optional<DeliveryEntity> findByIdAndAppUser_Id(UUID id, UUID appUserId);

    /**
     * Fetches the delivery by publicCodeClient with the AppUser and its payment methods
     * loaded in a single query — no N+1.
     */
    @Query("""
            SELECT DISTINCT d FROM DeliveryEntity d
            LEFT JOIN FETCH d.appUser u
            LEFT JOIN FETCH u.paymentMethods
            WHERE d.publicCodeClient = :publicCodeClient
            """)
    Optional<DeliveryEntity> findByPublicCodeClientWithPaymentMethods(
            @Param("publicCodeClient") UUID publicCodeClient
    );
}
