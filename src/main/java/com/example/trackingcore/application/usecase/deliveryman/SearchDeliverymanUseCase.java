package com.example.trackingcore.application.usecase.deliveryman;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.deliveryman.input.SearchDeliverymanInput;
import com.example.trackingcore.application.usecase.deliveryman.output.SearchDeliverymanOutput;
import com.example.trackingcore.domain.port.DeliverymanGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SearchDeliverymanUseCase extends UseCase<SearchDeliverymanInput, List<SearchDeliverymanOutput>> {

    private final DeliverymanGateway deliverymanGateway;

    public SearchDeliverymanUseCase(final DeliverymanGateway deliverymanGateway) {
        this.deliverymanGateway = deliverymanGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchDeliverymanOutput> execute(final SearchDeliverymanInput input) {
        if (input.q() == null || input.q().trim().length() < 2) {
            return Collections.emptyList();
        }

        return deliverymanGateway.search(input.q().trim())
                .stream()
                .map(d -> new SearchDeliverymanOutput(
                        d.getId(),
                        d.getName() + " — " + d.getPhoneNumber(),
                        d.getName()
                ))
                .toList();
    }
}


