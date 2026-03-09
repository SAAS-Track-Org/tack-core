package com.example.trackingcore.application.usecase.order;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.order.output.StandbyOrderSummaryOutput;
import com.example.trackingcore.domain.port.OrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListStandbyOrdersUseCase extends UseCase<Void, List<StandbyOrderSummaryOutput>> {

    private final OrderGateway orderGateway;

    public ListStandbyOrdersUseCase(final OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandbyOrderSummaryOutput> execute(final Void input) {
        return orderGateway.findAllStandby().stream()
                .map(order -> {
                    final var clientName = order.getClient() != null
                            && order.getClient().getName() != null
                            && !order.getClient().getName().isBlank()
                            && !"Pendente".equals(order.getClient().getName())
                            ? order.getClient().getName()
                            : null;
                    final var displayName = clientName != null ? clientName : "Cliente não informado";
                    return new StandbyOrderSummaryOutput(
                            order.getCode(),
                            clientName,
                            order.getDeliveryId(),
                            order.getCode() + " - " + displayName
                    );
                })
                .toList();
    }
}

