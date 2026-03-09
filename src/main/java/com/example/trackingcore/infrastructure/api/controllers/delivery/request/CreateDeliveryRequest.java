package com.example.trackingcore.infrastructure.api.controllers.delivery.request;

import com.example.trackingcore.infrastructure.api.controllers.deliveryman.request.DeliverymanRequest;
import com.example.trackingcore.infrastructure.api.controllers.order.request.OrderRequest;

import java.util.List;

public record CreateDeliveryRequest(
        DeliverymanRequest deliveryman,
        List<OrderRequest> orders
) {}
