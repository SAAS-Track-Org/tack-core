package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PlatformConfig {

    private final UUID id;
    private final int sessionDurationHours;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static PlatformConfig create(int sessionDurationHours) {
        final var id = UUID.randomUUID();
        final var now = LocalDateTime.now();
        return new PlatformConfig(id, sessionDurationHours, now, now);
    }

    public static PlatformConfig with(
            UUID id,
            int sessionDurationHours,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new PlatformConfig(id, sessionDurationHours, createdAt, updatedAt);
    }

    private PlatformConfig(UUID id, int sessionDurationHours, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sessionDurationHours = sessionDurationHours;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

