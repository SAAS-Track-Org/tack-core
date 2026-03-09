package com.example.trackingcore.infrastructure.persistence.client;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    @Query("SELECT c FROM ClientEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<ClientEntity> search(@Param("q") String q, Pageable pageable);
}
