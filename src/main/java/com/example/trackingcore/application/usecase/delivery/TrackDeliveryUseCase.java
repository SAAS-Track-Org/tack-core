package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.TrackDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.output.TrackDeliveryOutput;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.DeliveryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrackDeliveryUseCase extends UseCase<TrackDeliveryInput, TrackDeliveryOutput> {

    private final DeliveryGateway deliveryGateway;

    public TrackDeliveryUseCase(final DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public TrackDeliveryOutput execute(final TrackDeliveryInput input) {
        final var delivery = deliveryGateway.findByPublicCodeClient(input.publicCodeClient())
                .orElseThrow(() -> new NotFoundException(
                        "Delivery not found for public code: " + input.publicCodeClient()
                ));

        final var order = delivery.getOrders().stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.DELETED
                        && o.getDeliveryStatus() != OrderStatus.STANDBY)
                .filter(o -> o.getCode().equals(input.orderCode()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Order not found with code: " + input.orderCode()
                ));

        final var orderOutput = new TrackDeliveryOutput.OrderOutput(order.getCode());

        return new TrackDeliveryOutput(
                delivery.getStatus().name(),
                delivery.getDeliveryman().getName(),
                delivery.getCurrentLat(),
                delivery.getCurrentLng(),
                orderOutput
        );
    }
}
