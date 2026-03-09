package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.TrackOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.ClientTrackInput;
import com.example.trackingcore.application.usecase.delivery.output.ClientTrackOutput;
import com.example.trackingcore.domain.model.enums.OrderStatus;
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

        final var delivery = deliveryGateway.findByPublicCodeClient(input.publicCodeClient())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found for public code: " + input.publicCodeClient()
                ));

        final var order = delivery.getOrders().stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.DELETED
                        && o.getDeliveryStatus() != OrderStatus.STANDBY)
                .filter(o -> o.getCode().equals(input.orderCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found with code: " + input.orderCode()
                ));

        return MAPPER.toClientTrackOutput(order, delivery);
    }
}
