package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Deliveryman;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliverymanGateway {

    Deliveryman save(Deliveryman deliveryman);

    Optional<Deliveryman> findById(UUID id);

    List<Deliveryman> search(String q);
}