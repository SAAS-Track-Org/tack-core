package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.PaymentMethod;

import java.util.List;

/**
 * Projection returned by the track query — carries the matched order, its parent delivery,
 * and the establishment's available payment methods, all loaded in a single DB round-trip.
 */
public record DeliveryTrackResult(
        Order order,
        Delivery delivery,
        List<PaymentMethod> availablePaymentMethods
) {}

