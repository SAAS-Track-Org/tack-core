package com.example.trackingcore.application.usecase.client;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.client.input.ListAddressesByClientIdInput;
import com.example.trackingcore.application.usecase.client.output.ListAddressesByClientIdOutput;
import com.example.trackingcore.domain.port.AddressGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListAddressesByClientIdUseCase extends UseCase<ListAddressesByClientIdInput, List<ListAddressesByClientIdOutput>> {

    private final AddressGateway addressGateway;

    public ListAddressesByClientIdUseCase(final AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListAddressesByClientIdOutput> execute(final ListAddressesByClientIdInput input) {
        return addressGateway.findByClientId(input.clientId())
                .stream()
                .map(address -> new ListAddressesByClientIdOutput(
                        address.getId(),
                        address.getZipCode(),
                        address.getStreet()
                ))
                .toList();
    }
}
