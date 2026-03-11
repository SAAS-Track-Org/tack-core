package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.port.OrderGateway;
import com.example.trackingcore.infrastructure.mapper.ClientInfraMapper;
import com.example.trackingcore.infrastructure.mapper.OrderInfraMapper;
import com.example.trackingcore.infrastructure.persistence.client.ClientRepository;
import com.example.trackingcore.infrastructure.persistence.delivery.DeliveryRepository;
import com.example.trackingcore.infrastructure.persistence.order.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.insecure;

@Component
public class OrderGatewayImpl implements OrderGateway {

    private static final OrderInfraMapper ORDER_INFRA_MAPPER = OrderInfraMapper.INSTANCE;
    private static final ClientInfraMapper CLIENT_INFRA_MAPPER = ClientInfraMapper.INSTANCE;
    private static final int PREFIX_LENGTH = 3;

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ClientRepository clientRepository;

    public OrderGatewayImpl(
            final OrderRepository orderRepository,
            final DeliveryRepository deliveryRepository,
            final ClientRepository clientRepository
    ) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Order save(final Order order) {
        // Persist client updates (name / phone) — no cascade on the @ManyToOne
        if (order.getClient() != null) {
            clientRepository.save(CLIENT_INFRA_MAPPER.toEntity(order.getClient()));
        }
        return ORDER_INFRA_MAPPER.fromEntity(
                orderRepository.save(ORDER_INFRA_MAPPER.toEntity(order))
        );
    }

    @Override
    public Order saveToDelivery(final UUID deliveryId, final Order order) {
        final var delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + deliveryId));

        final var orderEntity = ORDER_INFRA_MAPPER.toEntity(order);
        delivery.getOrders().add(orderEntity);
        deliveryRepository.save(delivery);

        // Re-fetch to get the managed entity with delivery_id set
        return ORDER_INFRA_MAPPER.fromEntity(
                orderRepository.findByDeliveryIdAndCode(deliveryId, order.getCode())
                        .orElseThrow()
        );
    }

    @Override
    public String generateOrderCode() {
        final var prefix = insecure().nextAlphabetic(PREFIX_LENGTH).toUpperCase();
        return prefix + "-" + orderRepository.nextCode();
    }

    @Override
    public List<Order> findByDeliveryId(final UUID deliveryId) {
        return orderRepository.findByDeliveryId(deliveryId).stream()
                .map(ORDER_INFRA_MAPPER::fromEntity)
                .toList();
    }

    @Override
    public List<Order> findByDeliveryIdFiltered(final UUID deliveryId, final List<OrderStatus> excludedStatuses) {
        final var excluded = excludedStatuses.stream().map(Enum::name).toList();
        return orderRepository.findByDeliveryIdAndStatusOrderNotIn(deliveryId, excluded).stream()
                .map(ORDER_INFRA_MAPPER::fromEntity)
                .toList();
    }

    @Override
    public List<Order> findAllStandby() {
        return orderRepository.findAllStandby().stream()
                .map(ORDER_INFRA_MAPPER::fromEntity)
                .toList();
    }

    @Override
    public Order relinkToDelivery(final UUID newDeliveryId, final Order order) {
        // Remove from old delivery's list
        if (order.getDeliveryId() != null) {
            deliveryRepository.findById(order.getDeliveryId()).ifPresent(oldDelivery -> {
                oldDelivery.getOrders().removeIf(o -> o.getId().equals(order.getId()));
                deliveryRepository.save(oldDelivery);
            });
        }

        // Add to new delivery's list (cascade sets new delivery_id FK)
        final var newDelivery = deliveryRepository.findById(newDeliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + newDeliveryId));

        // Update the order entity's mutable fields (statusOrder) via direct save first
        final var orderEntity = orderRepository.findById(order.getId()).orElseThrow();
        orderEntity.setStatusOrder(order.getDeliveryStatus().name());
        orderRepository.save(orderEntity);

        // Attach to new delivery (sets delivery_id via @JoinColumn cascade)
        newDelivery.getOrders().add(orderEntity);
        deliveryRepository.save(newDelivery);

        return ORDER_INFRA_MAPPER.fromEntity(
                orderRepository.findByDeliveryIdAndCode(newDeliveryId, order.getCode())
                        .orElseThrow()
        );
    }

    @Override
    public Optional<Order> findByCode(final String code) {
        return orderRepository.findByCode(code)
                .map(ORDER_INFRA_MAPPER::fromEntity);
    }

    @Override
    public Optional<Order> findByDeliveryIdAndCode(final UUID deliveryId, final String code) {
        return orderRepository.findByDeliveryIdAndCode(deliveryId, code)
                .map(ORDER_INFRA_MAPPER::fromEntity);
    }
}
