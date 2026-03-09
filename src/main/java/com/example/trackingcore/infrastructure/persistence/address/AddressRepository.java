package com.example.trackingcore.infrastructure.persistence.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    @Query("""
            SELECT a FROM AddressEntity a
            WHERE a.id IN (
                SELECT o.deliveryAddress.id FROM OrderEntity o
                WHERE o.client.id = ?1 AND o.deliveryAddress IS NOT NULL
            )
            """)
    List<AddressEntity> findByClientId(UUID clientId);
}
