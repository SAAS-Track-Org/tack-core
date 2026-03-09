package com.example.trackingcore.infrastructure.api.controllers.address;

import com.example.trackingcore.application.usecase.address.GetAddressByIdUseCase;
import com.example.trackingcore.application.usecase.address.input.GetAddressByIdInput;
import com.example.trackingcore.infrastructure.api.AddressApi;
import com.example.trackingcore.infrastructure.api.controllers.address.response.GetAddressByIdResponse;
import com.example.trackingcore.infrastructure.mapper.AddressInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AddressController implements AddressApi {

    private static final AddressInfraMapper ADDRESS_INFRA_MAPPER = AddressInfraMapper.INSTANCE;

    private final GetAddressByIdUseCase getAddressByIdUseCase;

    public AddressController(final GetAddressByIdUseCase getAddressByIdUseCase) {
        this.getAddressByIdUseCase = getAddressByIdUseCase;
    }

    @Override
    public GetAddressByIdResponse getById(final UUID addressId) {
        final var output = getAddressByIdUseCase.execute(new GetAddressByIdInput(addressId));
        return ADDRESS_INFRA_MAPPER.toGetAddressByIdResponse(output);
    }
}

