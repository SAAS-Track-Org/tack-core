package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.PaymentMethod;
import com.example.trackingcore.domain.port.PaymentMethodGateway;
import com.example.trackingcore.infrastructure.mapper.PaymentMethodInfraMapper;
import com.example.trackingcore.infrastructure.persistence.paymentmethod.PaymentMethodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMethodGatewayImpl implements PaymentMethodGateway {

    private static final PaymentMethodInfraMapper MAPPER = PaymentMethodInfraMapper.INSTANCE;

    private final PaymentMethodJpaRepository paymentMethodJpaRepository;

    public PaymentMethodGatewayImpl(final PaymentMethodJpaRepository paymentMethodJpaRepository) {
        this.paymentMethodJpaRepository = paymentMethodJpaRepository;
    }

    @Override
    public List<PaymentMethod> findAllByIds(final List<String> ids) {
        return paymentMethodJpaRepository.findAllById(ids)
                .stream()
                .map(MAPPER::fromEntity)
                .toList();
    }
}

