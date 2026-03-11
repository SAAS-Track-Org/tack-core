package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.TrackOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.ClientTrackInput;
import com.example.trackingcore.application.usecase.delivery.output.ClientTrackOutput;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.port.DeliveryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetClientTrackUseCase extends UseCase<ClientTrackInput, ClientTrackOutput> {

    private final DeliveryGateway deliveryGateway;

    private static final TrackOutputMapper MAPPER = TrackOutputMapper.INSTANCE;

    public GetClientTrackUseCase(final DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientTrackOutput execute(final ClientTrackInput input) {

        final var result = deliveryGateway
                .findTrackByPublicCodeClient(input.publicCodeClient(), input.orderCode())
                .orElseThrow(() -> new NotFoundException(
                        "Delivery or order not found for public code: " + input.publicCodeClient()
                                + " / order: " + input.orderCode()
                ));

        if (result.order() == null) {
            throw new NotFoundException("Order not found with code: " + input.orderCode());
        }

        return MAPPER.toClientTrackOutput(result.order(), result.delivery(), result.availablePaymentMethods());
    }
}
