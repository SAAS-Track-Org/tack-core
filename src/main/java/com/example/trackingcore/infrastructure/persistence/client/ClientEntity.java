package com.example.trackingcore.infrastructure.persistence.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    private LocalDateTime createdAt;
}


