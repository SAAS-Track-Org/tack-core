package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.GetDeliveryDetailInput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetDeliveryDetailUseCase extends UseCase<GetDeliveryDetailInput, DeliveryDetailOutput> {

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
    public DeliveryDetailOutput execute(final GetDeliveryDetailInput input) {
        final var delivery = deliveryGateway.findByIdAndAppUserId(input.deliveryId(), input.appUserId())
                .orElseThrow(() -> NotFoundException.of("Delivery", input.deliveryId()));

        final var orderDetails = orderGateway
                .findByDeliveryIdFiltered(input.deliveryId(), EXCLUDED_STATUSES)
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
