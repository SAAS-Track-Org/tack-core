package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Address {
    private final UUID id;
    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public static Address create(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {
        final var id = UUID.randomUUID();
        return new Address(id, street, number, complement, neighborhood, city, state, zipCode, country);
    }

    public static Address with(
            UUID id,
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {
        return new Address(id, street, number, complement, neighborhood, city, state, zipCode, country);
    }

    private Address(
            UUID id,
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode,
            String country
    ) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;

        this.validate();
    }

    private void validate() {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or blank");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("ZipCode cannot be null or blank");
        }
    }
}

