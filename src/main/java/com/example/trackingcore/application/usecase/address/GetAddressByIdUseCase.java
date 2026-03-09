package com.example.trackingcore.application.usecase.address;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.address.input.GetAddressByIdInput;
import com.example.trackingcore.application.usecase.address.output.GetAddressByIdOutput;
import com.example.trackingcore.domain.port.AddressGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAddressByIdUseCase extends UseCase<GetAddressByIdInput, GetAddressByIdOutput> {

    private final AddressGateway addressGateway;

    public GetAddressByIdUseCase(final AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public GetAddressByIdOutput execute(final GetAddressByIdInput input) {
        final var address = addressGateway.findById(input.addressId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Address not found with id: " + input.addressId()
                ));

        return new GetAddressByIdOutput(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry()
        );
    }
}

