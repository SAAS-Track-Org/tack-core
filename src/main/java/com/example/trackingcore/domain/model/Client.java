package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Client {
    private final UUID id;
    private final String name;
    private final String phoneNumber;
    private final LocalDateTime createdAt;


    public static Client create(String name, String phoneNumber) {
        final var id = UUID.randomUUID();
        final var createdAt = LocalDateTime.now();
        return new Client(id, name, phoneNumber, createdAt);
    }

    public static Client with(
            UUID id,
            String name,
            String phoneNumber,
            LocalDateTime createdAt
    ) {
        return new Client(id, name, phoneNumber, createdAt);
    }

    public Client withName(final String newName) {
        return new Client(this.id, newName, this.phoneNumber, this.createdAt);
    }

    public Client withContact(final String newName, final String newPhone) {
        return new Client(this.id, newName, newPhone, this.createdAt);
    }

    private Client(UUID id, String name, String phoneNumber, LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
}
