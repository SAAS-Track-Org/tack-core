package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Client;
import com.example.trackingcore.domain.port.ClientGateway;
import com.example.trackingcore.infrastructure.mapper.ClientInfraMapper;
import com.example.trackingcore.infrastructure.persistence.client.ClientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ClientGatewayImpl implements ClientGateway {

    private static final ClientInfraMapper CLIENT_INFRA_MAPPER = ClientInfraMapper.INSTANCE;

    private final ClientRepository clientRepository;

    public ClientGatewayImpl(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(final Client client) {
        return CLIENT_INFRA_MAPPER.fromEntity(
                clientRepository.save(CLIENT_INFRA_MAPPER.toEntity(client))
        );
    }

    @Override
    public Optional<Client> findById(final UUID id) {
        return clientRepository.findById(id)
                .map(CLIENT_INFRA_MAPPER::fromEntity);
    }

    @Override
    public List<Client> search(final String q) {
        return clientRepository.search(q, PageRequest.of(0, 10))
                .stream()
                .map(CLIENT_INFRA_MAPPER::fromEntity)
                .toList();
    }
}
