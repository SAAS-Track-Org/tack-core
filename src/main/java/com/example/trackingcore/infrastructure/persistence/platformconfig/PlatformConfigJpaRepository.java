package com.example.trackingcore.infrastructure.persistence.platformconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlatformConfigJpaRepository extends JpaRepository<PlatformConfigJpaEntity, UUID> {
}

