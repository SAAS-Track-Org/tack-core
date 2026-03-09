package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientGateway {

    Client save(Client client);

    Optional<Client> findById(UUID id);

    List<Client> search(String q);
}