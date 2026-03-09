package com.example.trackingcore.infrastructure.api.controllers.order;

import com.example.trackingcore.application.usecase.order.ListStandbyOrdersUseCase;
import com.example.trackingcore.infrastructure.api.OrderApi;
import com.example.trackingcore.infrastructure.api.controllers.order.response.StandbyOrderResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController implements OrderApi {

    private final ListStandbyOrdersUseCase listStandbyOrdersUseCase;

    public OrderController(final ListStandbyOrdersUseCase listStandbyOrdersUseCase) {
        this.listStandbyOrdersUseCase = listStandbyOrdersUseCase;
    }

    @Override
    public List<StandbyOrderResponse> listStandby() {
        return listStandbyOrdersUseCase.execute(null).stream()
                .map(o -> new StandbyOrderResponse(
                        o.code(),
                        o.clientName(),
                        o.deliveryId(),
                        o.label()
                ))
                .toList();
    }
}
