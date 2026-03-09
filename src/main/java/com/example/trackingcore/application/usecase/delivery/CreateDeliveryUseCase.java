package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.output.CreateDeliveryOutput;
import com.example.trackingcore.domain.model.Client;
import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.model.Deliveryman;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.port.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateDeliveryUseCase extends UseCase<Void, CreateDeliveryOutput> {

    private final DeliveryGateway deliveryGateway;
    private final DeliverymanGateway deliverymanGateway;
    private final ClientGateway clientGateway;
    private final OrderGateway orderGateway;

    public CreateDeliveryUseCase(
            DeliveryGateway deliveryGateway,
            DeliverymanGateway deliverymanGateway,
            ClientGateway clientGateway,
            OrderGateway orderGateway
    ) {
        this.deliveryGateway = deliveryGateway;
        this.deliverymanGateway = deliverymanGateway;
        this.clientGateway = clientGateway;
        this.orderGateway = orderGateway;
    }

    @Override
    @Transactional
    public CreateDeliveryOutput execute(final Void input) {
        final var deliveryman = resolveDeliveryman();
        final var orders = resolveOrders();
        final var delivery = Delivery.create(deliveryman, orders);
        final var savedDelivery = deliveryGateway.save(delivery);

        final var orderOutputs = savedDelivery.getOrders().stream()
                .map(order -> new CreateDeliveryOutput.OrderOutput(
                        order.getCode(),
                        order.getClient().getName(),
                        order.getStatus(),
                        null
                ))
                .toList();

        return new CreateDeliveryOutput(
                savedDelivery.getId(),
                savedDelivery.getPublicCodeClient(),
                savedDelivery.getPublicCodeDeliveryman(),
                savedDelivery.getStatus(),
                orderOutputs
        );
    }

    private Deliveryman resolveDeliveryman() {
        final var newDeliveryman = Deliveryman.create("", "");
        return deliverymanGateway.save(newDeliveryman);
    }

    private List<Order> resolveOrders() {
        final var newClient = Client.create("", "");
        final var savedClient = clientGateway.save(newClient);
        final var code = orderGateway.generateOrderCode();
        return List.of(Order.create(savedClient, code));
    }
}