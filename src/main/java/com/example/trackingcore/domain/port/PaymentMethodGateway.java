package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodGateway {

    List<PaymentMethod> findAllByIds(List<String> ids);
}