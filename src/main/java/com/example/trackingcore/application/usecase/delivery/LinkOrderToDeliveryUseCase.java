package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.LinkOrderToDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LinkOrderToDeliveryUseCase extends UseCase<LinkOrderToDeliveryInput, DeliveryDetailOutput.OrderDetailOutput> {

    private final OrderGateway orderGateway;
    private final DeliveryGateway deliveryGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;


    public LinkOrderToDeliveryUseCase(
            final OrderGateway orderGateway,
            final DeliveryGateway deliveryGateway
    ) {
        this.orderGateway = orderGateway;
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional
    public DeliveryDetailOutput.OrderDetailOutput execute(final LinkOrderToDeliveryInput input) {
        final var order = orderGateway.findByCode(input.orderCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found: " + input.orderCode()
                ));

        if (order.getDeliveryStatus() != OrderStatus.STANDBY) {
            throw new IllegalStateException(
                    "Order " + input.orderCode() + " is not in STANDBY status. Current status: " + order.getDeliveryStatus()
            );
        }

        final var delivery = deliveryGateway.findById(input.deliveryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found: " + input.deliveryId()
                ));

        if (delivery.getStatus() == DeliveryStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot link order to a CANCELLED delivery: " + input.deliveryId()
            );
        }

        final var relinked = order.relinkToDelivery(input.deliveryId());
        final var saved = orderGateway.relinkToDelivery(input.deliveryId(), relinked);

        return MAPPER.toOrderDetailOutput(saved);
    }
}

