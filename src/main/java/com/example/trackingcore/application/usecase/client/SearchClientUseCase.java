package com.example.trackingcore.application.usecase.client;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.client.input.SearchClientInput;
import com.example.trackingcore.application.usecase.client.output.SearchClientOutput;
import com.example.trackingcore.domain.port.ClientGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SearchClientUseCase extends UseCase<SearchClientInput, List<SearchClientOutput>> {

    private final ClientGateway clientGateway;

    public SearchClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchClientOutput> execute(final SearchClientInput input) {
        if (input.q() == null || input.q().trim().length() < 2) {
            return Collections.emptyList();
        }

        return clientGateway.search(input.q().trim())
                .stream()
                .map(c -> new SearchClientOutput(
                        c.getId(),
                        c.getPhoneNumber() + " — " + c.getName(),
                        c.getPhoneNumber(),
                        c.getName()
                ))
                .toList();
    }
}

