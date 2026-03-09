package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.DriverDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.output.DriverDeliveryOutput;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.DeliveryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverDeliveryUseCase extends UseCase<DriverDeliveryInput, DriverDeliveryOutput> {

    private final DeliveryGateway deliveryGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;


    public DriverDeliveryUseCase(
            final DeliveryGateway deliveryGateway
    ) {
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDeliveryOutput execute(final DriverDeliveryInput input) {
        final var delivery = deliveryGateway.findByPublicCodeDeliveryman(input.publicCodeDeliveryman())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found for driver code: " + input.publicCodeDeliveryman()
                ));

        final var orderOutputs = delivery.getOrders().stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.DELETED
                        && o.getDeliveryStatus() != OrderStatus.STANDBY)
                .map(MAPPER::toDriverOrderOutput)
                .toList();

        return new DriverDeliveryOutput(
                delivery.getId(),
                delivery.getPublicCodeDeliveryman(),
                delivery.getStatus().name(),
                orderOutputs
        );
    }
}
