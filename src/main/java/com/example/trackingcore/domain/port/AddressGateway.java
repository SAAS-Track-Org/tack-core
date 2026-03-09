package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressGateway {

    Address save(Address address);

    Optional<Address> findById(UUID id);

    List<Address> findByClientId(UUID clientId);
}
