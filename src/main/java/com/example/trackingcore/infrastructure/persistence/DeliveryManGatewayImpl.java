package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Deliveryman;
import com.example.trackingcore.domain.port.DeliverymanGateway;
import com.example.trackingcore.infrastructure.mapper.DeliveryManInfraMapper;
import com.example.trackingcore.infrastructure.persistence.deliveryman.DeliveryManRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DeliveryManGatewayImpl implements DeliverymanGateway {

    private static final DeliveryManInfraMapper DELIVERY_MAN_INFRA_MAPPER = DeliveryManInfraMapper.INSTANCE;

    private final DeliveryManRepository deliveryManRepository;

    public DeliveryManGatewayImpl(DeliveryManRepository deliveryManRepository) {
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    public Deliveryman save(Deliveryman deliveryman) {
        final var deliverymanEntity = DELIVERY_MAN_INFRA_MAPPER.toEntity(deliveryman);
        return DELIVERY_MAN_INFRA_MAPPER.fromEntity(deliveryManRepository.save(deliverymanEntity));
    }

    @Override
    public Optional<Deliveryman> findById(UUID id) {
        return deliveryManRepository.findById(id)
                .map(DELIVERY_MAN_INFRA_MAPPER::fromEntity);
    }

    @Override
    public List<Deliveryman> search(String q) {
        return deliveryManRepository.search(q, PageRequest.of(0, 10))
                .stream()
                .map(DELIVERY_MAN_INFRA_MAPPER::fromEntity)
                .toList();
    }
}
