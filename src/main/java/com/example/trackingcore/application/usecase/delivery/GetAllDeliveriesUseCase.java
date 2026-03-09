package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.mapper.DeliveryOutputMapper;
import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.output.GetAllDeliveriesOutput;
import com.example.trackingcore.domain.port.DeliveryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllDeliveriesUseCase extends UseCase<Void, List<GetAllDeliveriesOutput>> {

    private final DeliveryGateway deliveryGateway;

    private static final DeliveryOutputMapper MAPPER = DeliveryOutputMapper.INSTANCE;


    public GetAllDeliveriesUseCase(final DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAllDeliveriesOutput> execute(final Void input) {
        return deliveryGateway.findAll().stream()
                .map(MAPPER::toGetAllDeliveriesOutput)
                .toList();
    }
}
