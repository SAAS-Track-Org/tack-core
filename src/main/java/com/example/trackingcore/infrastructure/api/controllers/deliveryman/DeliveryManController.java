package com.example.trackingcore.infrastructure.api.controllers.deliveryman;

import com.example.trackingcore.application.usecase.deliveryman.GetDeliverymanByIdUseCase;
import com.example.trackingcore.application.usecase.deliveryman.SearchDeliverymanUseCase;
import com.example.trackingcore.application.usecase.deliveryman.input.GetDeliverymanByIdInput;
import com.example.trackingcore.application.usecase.deliveryman.input.SearchDeliverymanInput;
import com.example.trackingcore.infrastructure.api.DeliveryManApi;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.request.CreateDeliveryManRequest;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.GetDeliverymanByIdResponse;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.SearchDeliverymanResponse;
import com.example.trackingcore.infrastructure.mapper.DeliveryManInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DeliveryManController implements DeliveryManApi {

    private static final DeliveryManInfraMapper DELIVERY_MAN_INFRA_MAPPER = DeliveryManInfraMapper.INSTANCE;

    private final SearchDeliverymanUseCase searchDeliverymanUseCase;
    private final GetDeliverymanByIdUseCase getDeliverymanByIdUseCase;

    public DeliveryManController(
            final SearchDeliverymanUseCase searchDeliverymanUseCase,
            final GetDeliverymanByIdUseCase getDeliverymanByIdUseCase
    ) {
        this.searchDeliverymanUseCase = searchDeliverymanUseCase;
        this.getDeliverymanByIdUseCase = getDeliverymanByIdUseCase;
    }

    @Override
    public void createDeliveryMan(CreateDeliveryManRequest request) {
    }

    @Override
    public List<SearchDeliverymanResponse> search(final String q) {
        final var output = searchDeliverymanUseCase.execute(new SearchDeliverymanInput(q));
        return output.stream()
                .map(DELIVERY_MAN_INFRA_MAPPER::toSearchDeliverymanResponse)
                .toList();
    }

    @Override
    public GetDeliverymanByIdResponse getById(final UUID id) {
        final var output = getDeliverymanByIdUseCase.execute(new GetDeliverymanByIdInput(id));
        return DELIVERY_MAN_INFRA_MAPPER.toGetDeliverymanByIdResponse(output);
    }
}

