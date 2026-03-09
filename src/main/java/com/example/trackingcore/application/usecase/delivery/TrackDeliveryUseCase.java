package com.example.trackingcore.application.usecase.delivery;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.delivery.input.TrackDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.output.TrackDeliveryOutput;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.OrderStatus;

import com.example.trackingcore.domain.model.OrderProduct;
import com.example.trackingcore.domain.model.Product;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.domain.port.OrderProductGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TrackDeliveryUseCase extends UseCase<TrackDeliveryInput, TrackDeliveryOutput> {

    private final DeliveryGateway deliveryGateway;
    private final OrderProductGateway orderProductGateway;

    public TrackDeliveryUseCase(
            final DeliveryGateway deliveryGateway,
            final OrderProductGateway orderProductGateway
    ) {
        this.deliveryGateway = deliveryGateway;
        this.orderProductGateway = orderProductGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public TrackDeliveryOutput execute(final TrackDeliveryInput input) {
        final var delivery = deliveryGateway.findByPublicCodeClient(input.publicCodeClient())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery not found for public code: " + input.publicCodeClient()
                ));

        final var order = delivery.getOrders().stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.DELETED
                        && o.getDeliveryStatus() != OrderStatus.STANDBY)
                .filter(o -> o.getCode().equals(input.orderCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found with code: " + input.orderCode()
                ));

        final var products = orderProductGateway.findByOrderId(order.getId())
                .stream()
                .map(OrderProduct::getProduct)
                .toList();

        final var orderOutput = buildOrderOutput(order, products);

        return new TrackDeliveryOutput(
                delivery.getStatus().name(),
                delivery.getDeliveryman().getName(),
                delivery.getCurrentLat(),
                delivery.getCurrentLng(),
                orderOutput
        );
    }

    private TrackDeliveryOutput.OrderOutput buildOrderOutput(final Order order, final List<Product> products) {
        final var totalAmount = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final var productOutputs = products.stream()
                .map(p -> new TrackDeliveryOutput.ProductOutput(p.getName(), p.getPrice()))
                .toList();

        return new TrackDeliveryOutput.OrderOutput(
                order.getCode(),
                totalAmount,
                productOutputs
        );
    }
}
