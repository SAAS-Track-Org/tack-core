package com.example.trackingcore.infrastructure.persistence.platformconfig;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "platform_config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatformConfigJpaEntity {

    @Id
    private UUID id;

    @Column(name = "session_duration_hours", nullable = false)
    private int sessionDurationHours;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

