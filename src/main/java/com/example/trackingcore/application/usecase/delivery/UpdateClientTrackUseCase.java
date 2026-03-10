package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.TrackOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.UpdateClientTrackInput;
import com.example.trackingcore.application.usecase.delivery.output.ClientTrackOutput;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.AddressGateway;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateClientTrackUseCase extends UseCase<UpdateClientTrackInput, ClientTrackOutput> {

    private final DeliveryGateway deliveryGateway;
    private final OrderGateway orderGateway;
    private final AddressGateway addressGateway;

    public UpdateClientTrackUseCase(
            final DeliveryGateway deliveryGateway,
            final OrderGateway orderGateway,
            final AddressGateway addressGateway
    ) {
        this.deliveryGateway = deliveryGateway;
        this.orderGateway = orderGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    @Transactional
    public ClientTrackOutput execute(final UpdateClientTrackInput input) {
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

        if (order.getDeliveryStatus() == OrderStatus.ARRIVING || order.getDeliveryStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Cannot update order address when status is: " + order.getDeliveryStatus()
            );
        }

        final Address address = buildAddress(input.address());
        final var saved = orderGateway.save(
                order.update(order.getNotes(), order.getTotalAmount(), input.paymentMethod(), address)
        );

        return TrackOutputMapper.INSTANCE.toClientTrackOutput(saved, delivery);
    }

    private Address buildAddress(final UpdateClientTrackInput.AddressInput input) {
        if (input == null) return null;
        return addressGateway.save(Address.create(
                input.street(), input.number(), input.complement(),
                input.neighborhood(), input.city(), input.state(),
                input.zipCode(), input.country()
        ));
    }
}
