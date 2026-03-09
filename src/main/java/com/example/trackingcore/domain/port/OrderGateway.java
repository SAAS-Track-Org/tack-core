package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderGateway {

    Order save(Order order);

    Order saveToDelivery(UUID deliveryId, Order order);

    Order relinkToDelivery(UUID newDeliveryId, Order order);

    String generateOrderCode();

    List<Order> findByDeliveryId(UUID deliveryId);

    List<Order> findByDeliveryIdFiltered(UUID deliveryId, List<OrderStatus> excludedStatuses);

    List<Order> findAllStandby();

    Optional<Order> findByCode(String code);

    Optional<Order> findByDeliveryIdAndCode(UUID deliveryId, String code);
}
