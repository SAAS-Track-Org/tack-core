package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Delivery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryGateway {

    Delivery save(Delivery delivery);

    Optional<Delivery> findById(UUID id);

    Optional<Delivery> findByIdAndAppUserId(UUID id, UUID appUserId);

    Optional<Delivery> findByPublicCodeClient(UUID publicCodeClient);

    Optional<Delivery> findByPublicCodeDeliveryman(UUID publicCodeDeliveryman);

    List<Delivery> findAll();

    List<Delivery> findAllByAppUserId(UUID appUserId);
}