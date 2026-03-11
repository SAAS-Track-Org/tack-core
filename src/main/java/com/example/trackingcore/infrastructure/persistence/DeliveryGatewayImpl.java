package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.port.DeliveryGateway;
import com.example.trackingcore.infrastructure.mapper.DeliveryInfraMapper;
import com.example.trackingcore.infrastructure.persistence.appuser.AppUserJpaRepository;
import com.example.trackingcore.infrastructure.persistence.delivery.DeliveryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DeliveryGatewayImpl implements DeliveryGateway {

    private final DeliveryRepository deliveryRepository;
    private final AppUserJpaRepository appUserJpaRepository;
    private static final DeliveryInfraMapper DELIVERY_MAPPER = DeliveryInfraMapper.INSTANCE;

    public DeliveryGatewayImpl(
            final DeliveryRepository deliveryRepository,
            final AppUserJpaRepository appUserJpaRepository
    ) {
        this.deliveryRepository = deliveryRepository;
        this.appUserJpaRepository = appUserJpaRepository;
    }

    @Override
    public Delivery save(final Delivery delivery) {
        final var entity = DELIVERY_MAPPER.toEntity(delivery);

        // Resolve and set the AppUser relationship
        if (delivery.getAppUserId() != null) {
            appUserJpaRepository.findById(delivery.getAppUserId())
                    .ifPresent(entity::setAppUser);
        }

        return DELIVERY_MAPPER.fromEntity(deliveryRepository.save(entity));
    }

    @Override
    public Optional<Delivery> findById(final UUID id) {
        return deliveryRepository.findById(id)
                .map(DELIVERY_MAPPER::fromEntity);
    }

    @Override
    public Optional<Delivery> findByIdAndAppUserId(final UUID id, final UUID appUserId) {
        return deliveryRepository.findByIdAndAppUser_Id(id, appUserId)
                .map(DELIVERY_MAPPER::fromEntity);
    }

    @Override
    public Optional<Delivery> findByPublicCodeClient(final UUID publicCodeClient) {
        return deliveryRepository.findByPublicCodeClient(publicCodeClient)
                .map(DELIVERY_MAPPER::fromEntity);
    }

    @Override
    public Optional<Delivery> findByPublicCodeDeliveryman(final UUID publicCodeDeliveryman) {
        return deliveryRepository.findByPublicCodeDeliveryman(publicCodeDeliveryman)
                .map(DELIVERY_MAPPER::fromEntity);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll().stream()
                .map(DELIVERY_MAPPER::fromEntity)
                .toList();
    }

    @Override
    public List<Delivery> findAllByAppUserId(final UUID appUserId) {
        return deliveryRepository.findAllByAppUser_Id(appUserId).stream()
                .map(DELIVERY_MAPPER::fromEntity)
                .toList();
    }
}
