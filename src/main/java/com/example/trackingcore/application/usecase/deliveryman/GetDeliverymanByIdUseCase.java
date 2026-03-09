package com.example.trackingcore.application.usecase.deliveryman;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.deliveryman.input.GetDeliverymanByIdInput;
import com.example.trackingcore.application.usecase.deliveryman.output.GetDeliverymanByIdOutput;
import com.example.trackingcore.domain.port.DeliverymanGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetDeliverymanByIdUseCase extends UseCase<GetDeliverymanByIdInput, GetDeliverymanByIdOutput> {

    private final DeliverymanGateway deliverymanGateway;

    public GetDeliverymanByIdUseCase(final DeliverymanGateway deliverymanGateway) {
        this.deliverymanGateway = deliverymanGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public GetDeliverymanByIdOutput execute(final GetDeliverymanByIdInput input) {
        final var deliveryman = deliverymanGateway.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Deliveryman not found with id: " + input.id()
                ));

        return new GetDeliverymanByIdOutput(
                deliveryman.getId(),
                deliveryman.getName(),
                deliveryman.getPhoneNumber()
        );
    }
}

