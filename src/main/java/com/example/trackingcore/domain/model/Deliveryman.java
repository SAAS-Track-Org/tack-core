package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Deliveryman {
    private final UUID id;
    private final String name;
    private final String phoneNumber;

    public static Deliveryman create(String name, String phoneNumber) {
        final var id = UUID.randomUUID();

        return new Deliveryman(id, name, phoneNumber);
    }

    private Deliveryman(UUID id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Deliveryman with(
            UUID id,
            String name,
            String phoneNumber
    ) {
        return new Deliveryman(id, name, phoneNumber);
    }

    public Deliveryman withContact(final String newName, final String newPhone) {
        return new Deliveryman(this.id, newName, newPhone);
    }
}
