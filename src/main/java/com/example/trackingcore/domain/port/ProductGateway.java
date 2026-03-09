package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {

    Optional<Product> findById(UUID id);

    Product save(Product product);

    List<Product> findAll();
}

