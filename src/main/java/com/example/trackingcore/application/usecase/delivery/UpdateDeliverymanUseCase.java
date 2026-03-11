package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.GetDeliveryDetailInput;
import com.example.trackingcore.application.usecase.delivery.input.UpdateDeliverymanInput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.DeliverymanGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateDeliverymanUseCase extends UseCase<UpdateDeliverymanInput, DeliveryDetailOutput> {

    private final DeliveryGateway deliveryGateway;
    private final DeliverymanGateway deliverymanGateway;
    private final GetDeliveryDetailUseCase getDeliveryDetailUseCase;

    public UpdateDeliverymanUseCase(
            final DeliveryGateway deliveryGateway,
            final DeliverymanGateway deliverymanGateway,
            final GetDeliveryDetailUseCase getDeliveryDetailUseCase
    ) {
        this.deliveryGateway = deliveryGateway;
        this.deliverymanGateway = deliverymanGateway;
        this.getDeliveryDetailUseCase = getDeliveryDetailUseCase;
    }

    @Override
    @Transactional
    public DeliveryDetailOutput execute(final UpdateDeliverymanInput input) {
        final var delivery = deliveryGateway.findById(input.deliveryId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + input.deliveryId()));

        final var deliveryman = delivery.getDeliveryman();
        if (deliveryman == null) {
            throw new IllegalArgumentException("Delivery has no deliveryman assigned: " + input.deliveryId());
        }

        final var newName  = (input.name() != null && !input.name().isBlank())
                ? input.name()  : deliveryman.getName();
        final var newPhone = (input.phoneNumber() != null && !input.phoneNumber().isBlank())
                ? input.phoneNumber() : deliveryman.getPhoneNumber();

        deliverymanGateway.save(deliveryman.withContact(newName, newPhone));

        return getDeliveryDetailUseCase.execute(new GetDeliveryDetailInput(input.deliveryId(), input.appUserId()));
    }
}
