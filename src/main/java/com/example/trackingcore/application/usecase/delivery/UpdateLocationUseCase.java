package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.UpdateLocationInput;
import com.example.trackingcore.application.usecase.delivery.output.UpdateLocationOutput;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.LocationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateLocationUseCase extends UseCase<UpdateLocationInput, UpdateLocationOutput> {

    private final DeliveryGateway deliveryGateway;
    private final LocationEventPublisher locationEventPublisher;

    public UpdateLocationUseCase(
            final DeliveryGateway deliveryGateway,
            final LocationEventPublisher locationEventPublisher
    ) {
        this.deliveryGateway = deliveryGateway;
        this.locationEventPublisher = locationEventPublisher;
    }

    @Override
    @Transactional
    public UpdateLocationOutput execute(final UpdateLocationInput input) {
        final var delivery = deliveryGateway.findByPublicCodeDeliveryman(input.publicCodeDeliveryman())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found for driver code: " + input.publicCodeDeliveryman()
                ));

        final var updated = delivery.updateLocation(input.lat(), input.lng());
        final var saved = deliveryGateway.save(updated);

        locationEventPublisher.publish(saved.getPublicCodeClient(), saved.getCurrentLat(), saved.getCurrentLng());

        return new UpdateLocationOutput(saved.getCurrentLat(), saved.getCurrentLng());
    }
}

