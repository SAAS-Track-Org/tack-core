package com.example.trackingcore.infrastructure.persistence.deliveryman;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeliveryManRepository extends JpaRepository<DeliveryManEntity, UUID> {

    @Query("SELECT d FROM DeliveryManEntity d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(d.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<DeliveryManEntity> search(@Param("q") String q, Pageable pageable);
}
