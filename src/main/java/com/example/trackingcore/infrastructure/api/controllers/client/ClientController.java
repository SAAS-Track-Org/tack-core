package com.example.trackingcore.infrastructure.api.controllers.client;

import com.example.trackingcore.application.usecase.client.ListAddressesByClientIdUseCase;
import com.example.trackingcore.application.usecase.client.SearchClientUseCase;
import com.example.trackingcore.application.usecase.client.input.ListAddressesByClientIdInput;
import com.example.trackingcore.application.usecase.client.input.SearchClientInput;
import com.example.trackingcore.infrastructure.api.ClientApi;
import com.example.trackingcore.infrastructure.api.controllers.client.response.ListAddressesResponse;
import com.example.trackingcore.infrastructure.api.controllers.client.response.SearchClientResponse;
import com.example.trackingcore.infrastructure.mapper.AddressInfraMapper;
import com.example.trackingcore.infrastructure.mapper.ClientInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientController implements ClientApi {

    private static final ClientInfraMapper CLIENT_INFRA_MAPPER = ClientInfraMapper.INSTANCE;
    private static final AddressInfraMapper ADDRESS_INFRA_MAPPER = AddressInfraMapper.INSTANCE;

    private final SearchClientUseCase searchClientUseCase;
    private final ListAddressesByClientIdUseCase listAddressesByClientIdUseCase;

    public ClientController(
            final SearchClientUseCase searchClientUseCase,
            final ListAddressesByClientIdUseCase listAddressesByClientIdUseCase
    ) {
        this.searchClientUseCase = searchClientUseCase;
        this.listAddressesByClientIdUseCase = listAddressesByClientIdUseCase;
    }

    @Override
    public List<SearchClientResponse> search(final String q) {
        final var output = searchClientUseCase.execute(new SearchClientInput(q));
        return output.stream()
                .map(CLIENT_INFRA_MAPPER::toSearchClientResponse)
                .toList();
    }

    @Override
    public List<ListAddressesResponse> listAddresses(final UUID clientId) {
        final var output = listAddressesByClientIdUseCase.execute(new ListAddressesByClientIdInput(clientId));
        return output.stream()
                .map(ADDRESS_INFRA_MAPPER::toListAddressesResponse)
                .toList();
    }
}


