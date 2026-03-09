package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.OrderProduct;
import com.example.trackingcore.domain.model.Product;
import com.example.trackingcore.domain.port.OrderProductGateway;
import com.example.trackingcore.infrastructure.mapper.OrderProductInfraMapper;
import com.example.trackingcore.infrastructure.persistence.orderproduct.OrderProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderProductGatewayImpl implements OrderProductGateway {

    private final OrderProductRepository orderProductRepository;

    private static final OrderProductInfraMapper ORDER_PRODUCT_INFRA_MAPPER = OrderProductInfraMapper.INSTANCE;

    public OrderProductGatewayImpl(
            OrderProductRepository orderProductRepository
    ) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<OrderProduct> saveAll(final Order order, final List<Product> products) {
        return products.stream()
                .map(product -> save(OrderProduct.with(order, product)))
                .toList();
    }

    @Override
    public List<OrderProduct> findByOrderId(final UUID orderId) {
        return orderProductRepository.findByIdOrderId(orderId).stream()
                .map(ORDER_PRODUCT_INFRA_MAPPER::fromEntity)
                .toList();
    }

    public OrderProduct save(final OrderProduct orderProduct) {
        final var orderProductEntity = ORDER_PRODUCT_INFRA_MAPPER.toEntity(orderProduct);
        return ORDER_PRODUCT_INFRA_MAPPER.fromEntity(orderProductRepository.save(orderProductEntity));
    }
}




