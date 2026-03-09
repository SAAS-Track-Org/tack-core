package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.UpdateOrderInput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.domain.port.AddressGateway;
import com.example.trackingcore.domain.port.ClientGateway;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UpdateOrderUseCase extends UseCase<UpdateOrderInput, DeliveryDetailOutput.OrderDetailOutput> {

    private final OrderGateway orderGateway;
    private final AddressGateway addressGateway;
    private final ClientGateway clientGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;

    public UpdateOrderUseCase(
            final OrderGateway orderGateway,
            final AddressGateway addressGateway,
            final ClientGateway clientGateway
    ) {
        this.orderGateway = orderGateway;
        this.addressGateway = addressGateway;
        this.clientGateway = clientGateway;
    }

    @Override
    @Transactional
    public DeliveryDetailOutput.OrderDetailOutput execute(final UpdateOrderInput input) {
        final var order = orderGateway.findByDeliveryIdAndCode(input.deliveryId(), input.orderCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found: " + input.orderCode() + " in delivery: " + input.deliveryId()
                ));

        // Update client name/phone if provided
        if ((input.clientName() != null && !input.clientName().isBlank())
                || (input.clientPhone() != null && !input.clientPhone().isBlank())) {
            final var current = order.getClient();
            final var newName = (input.clientName() != null && !input.clientName().isBlank())
                    ? input.clientName() : current.getName();
            final var newPhone = (input.clientPhone() != null && !input.clientPhone().isBlank())
                    ? input.clientPhone() : current.getPhoneNumber();
            clientGateway.save(current.withContact(newName, newPhone));
        }

        final Address address = buildAddress(input.address());
        final var saved = orderGateway.save(
                order.update(input.notes(), input.totalAmount(), input.paymentMethod(), address)
        );

        return MAPPER.toOrderDetailOutput(saved);
    }

    private Address buildAddress(final UpdateOrderInput.AddressInput input) {
        if (input == null) return null;
        return addressGateway.save(Address.create(
                input.street(), input.number(), input.complement(),
                input.neighborhood(), input.city(), input.state(),
                input.zipCode(), input.country()
        ));
    }
}
