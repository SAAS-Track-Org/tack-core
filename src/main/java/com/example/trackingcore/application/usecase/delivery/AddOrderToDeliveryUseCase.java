package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.model.Client;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.port.ClientGateway;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddOrderToDeliveryUseCase extends UseCase<UUID, DeliveryDetailOutput.OrderDetailOutput> {

    private final DeliveryGateway deliveryGateway;
    private final ClientGateway clientGateway;
    private final OrderGateway orderGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;


    public AddOrderToDeliveryUseCase(
            final DeliveryGateway deliveryGateway,
            final ClientGateway clientGateway,
            final OrderGateway orderGateway
    ) {
        this.deliveryGateway = deliveryGateway;
        this.clientGateway = clientGateway;
        this.orderGateway = orderGateway;
    }

    @Override
    @Transactional
    public DeliveryDetailOutput.OrderDetailOutput execute(final UUID deliveryId) {
        deliveryGateway.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + deliveryId));

        final var savedClient = clientGateway.save(Client.create("", ""));
        final var code = orderGateway.generateOrderCode();
        final var savedOrder = orderGateway.saveToDelivery(deliveryId, Order.create(savedClient, code));

        return MAPPER.toOrderDetailOutput(savedOrder);
    }
}
