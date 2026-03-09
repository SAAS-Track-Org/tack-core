package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.domain.port.AddressGateway;
import com.example.trackingcore.infrastructure.mapper.AddressInfraMapper;
import com.example.trackingcore.infrastructure.persistence.address.AddressRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AddressGatewayImpl implements AddressGateway {

    private static final AddressInfraMapper ADDRESS_INFRA_MAPPER = AddressInfraMapper.INSTANCE;

    private final AddressRepository addressRepository;

    public AddressGatewayImpl(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(final Address address) {
        return ADDRESS_INFRA_MAPPER.fromEntity(
                addressRepository.save(ADDRESS_INFRA_MAPPER.toEntity(address))
        );
    }

    @Override
    public Optional<Address> findById(final UUID id) {
        return addressRepository.findById(id)
                .map(ADDRESS_INFRA_MAPPER::fromEntity);
    }

    @Override
    public List<Address> findByClientId(final UUID clientId) {
        return addressRepository.findByClientId(clientId).stream()
                .map(ADDRESS_INFRA_MAPPER::fromEntity)
                .toList();
    }
}
