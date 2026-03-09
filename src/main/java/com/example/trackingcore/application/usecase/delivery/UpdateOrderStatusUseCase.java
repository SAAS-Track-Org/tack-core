package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.UpdateOrderStatusInput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UpdateOrderStatusUseCase extends UseCase<UpdateOrderStatusInput, DeliveryDetailOutput.OrderDetailOutput> {

    private final OrderGateway orderGateway;
    private final DeliveryGateway deliveryGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;

    public UpdateOrderStatusUseCase(final OrderGateway orderGateway, final DeliveryGateway deliveryGateway) {
        this.orderGateway = orderGateway;
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional
    public DeliveryDetailOutput.OrderDetailOutput execute(final UpdateOrderStatusInput input) {
        final var order = orderGateway.findByDeliveryIdAndCode(input.deliveryId(), input.orderCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found: " + input.orderCode() + " in delivery: " + input.deliveryId()
                ));

        final var delivery = deliveryGateway.findById(input.deliveryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found: " + input.deliveryId()
                ));

        final var saved = orderGateway.save(order.updateDeliveryStatus(input.deliveryStatus()));

        delivery.syncOrder(saved);

        boolean deliveryChanged = false;

        if (delivery.shouldInitiate(input.deliveryStatus())) {
            delivery.initiateDelivery();
            deliveryChanged = true;
        }

        if (delivery.isFullyCompleted()) {
            delivery.completeDelivery();
            deliveryChanged = true;
        }

        if (deliveryChanged) {
            deliveryGateway.save(delivery);
        }

        return MAPPER.toOrderDetailOutput(saved);
    }
}