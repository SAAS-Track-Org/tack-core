package com.example.trackingcore.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Product {
    private final UUID id;
    private final String name;
    private final String description;
    private final BigDecimal price;

    public static Product create(
            String name,
            String description,
            BigDecimal price,
            int quantity
    ) {
        final var id = UUID.randomUUID();
        return new Product(id, name, description, price);
    }

    public static Product with(
            UUID id,
            String name,
            String description,
            BigDecimal price
    ) {
        return new Product(id, name, description, price);
    }

    private Product(
            UUID id,
            String name,
            String description,
            BigDecimal price
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative");
        }
    }
}