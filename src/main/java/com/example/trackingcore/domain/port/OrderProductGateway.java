package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.OrderProduct;
import com.example.trackingcore.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface OrderProductGateway {

    List<OrderProduct> saveAll(Order order, List<Product> products);

    List<OrderProduct> findByOrderId(UUID orderId);
}

