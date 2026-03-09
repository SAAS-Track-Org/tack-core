package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GetDeliveryDetailUseCase extends UseCase<UUID, DeliveryDetailOutput> {

    private static final List<OrderStatus> EXCLUDED_STATUSES = List.of(OrderStatus.DELETED, OrderStatus.STANDBY);

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;


    private final DeliveryGateway deliveryGateway;
    private final OrderGateway orderGateway;

    public GetDeliveryDetailUseCase(final DeliveryGateway deliveryGateway,
                                    final OrderGateway orderGateway) {
        this.deliveryGateway = deliveryGateway;
        this.orderGateway = orderGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryDetailOutput execute(final UUID deliveryId) {
        final var delivery = deliveryGateway.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + deliveryId));

        final var orderDetails = orderGateway
                .findByDeliveryIdFiltered(deliveryId, EXCLUDED_STATUSES)
                .stream()
                .map(MAPPER::toOrderDetailOutput)
                .toList();

        return new DeliveryDetailOutput(
                delivery.getId(),
                delivery.getPublicCodeDeliveryman(),
                delivery.getPublicCodeClient(),
                delivery.getStatus(),
                delivery.getDeliveryman() != null ? delivery.getDeliveryman().getName() : null,
                delivery.getDeliveryman() != null ? delivery.getDeliveryman().getPhoneNumber() : null,
                delivery.getCreatedAt(),
                orderDetails
        );
    }
}
