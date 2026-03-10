package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.domain.model.PaymentMethod;
import com.example.trackingcore.infrastructure.persistence.paymentmethod.PaymentMethodJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMethodInfraMapper {

    PaymentMethodInfraMapper INSTANCE = Mappers.getMapper(PaymentMethodInfraMapper.class);

    default PaymentMethod fromEntity(final PaymentMethodJpaEntity entity) {
        if (entity == null) return null;
        return PaymentMethod.with(
                entity.getId(),
                entity.getLabel(),
                entity.getCreatedAt()
        );
    }
}

