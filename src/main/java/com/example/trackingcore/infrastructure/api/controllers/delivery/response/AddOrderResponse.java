package com.example.trackingcore.infrastructure.api.controllers.delivery.response;

import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import com.example.trackingcore.domain.model.enums.AddressStatus;

import java.math.BigDecimal;

public record AddOrderResponse(
        String code,
        String clientName,
        String clientPhone,
        AddressStatus addressStatus,
        OrderStatus deliveryStatus,
        String notes,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        DeliveryDetailResponse.AddressDetail address
) {}
