package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.domain.model.AppUser;
import com.example.trackingcore.infrastructure.persistence.appuser.AppUserJpaEntity;
import com.example.trackingcore.infrastructure.persistence.paymentmethod.PaymentMethodJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AppUserInfraMapper {

    AppUserInfraMapper INSTANCE = Mappers.getMapper(AppUserInfraMapper.class);

    PaymentMethodInfraMapper PAYMENT_METHOD_MAPPER = PaymentMethodInfraMapper.INSTANCE;

    default AppUser fromEntity(final AppUserJpaEntity entity) {
        if (entity == null) return null;
        final List<com.example.trackingcore.domain.model.PaymentMethod> paymentMethods =
                entity.getPaymentMethods() == null
                ? List.of()
                : entity.getPaymentMethods().stream()
                        .map(PAYMENT_METHOD_MAPPER::fromEntity)
                        .toList();
        return AppUser.with(
                entity.getId(),
                entity.getEmail(),
                paymentMethods,
                entity.getEstablishmentName(),
                entity.getAddress(),
                entity.getCreatedAt()
        );
    }

    default AppUserJpaEntity toEntity(final AppUser domain) {
        if (domain == null) return null;
        final List<PaymentMethodJpaEntity> paymentMethodEntities = domain.getPaymentMethods() == null
                ? new ArrayList<>()
                : domain.getPaymentMethods().stream()
                        .map(pm -> new PaymentMethodJpaEntity(pm.getId(), pm.getLabel(), pm.getCreatedAt()))
                        .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new AppUserJpaEntity(
                domain.getId(),
                domain.getEmail(),
                paymentMethodEntities,
                domain.getEstablishmentName(),
                domain.getAddress(),
                domain.getCreatedAt()
        );
    }
}
