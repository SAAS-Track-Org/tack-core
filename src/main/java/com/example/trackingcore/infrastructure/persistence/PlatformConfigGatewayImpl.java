package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.PlatformConfig;
import com.example.trackingcore.domain.port.PlatformConfigGateway;
import com.example.trackingcore.infrastructure.mapper.PlatformConfigInfraMapper;
import com.example.trackingcore.infrastructure.persistence.platformconfig.PlatformConfigJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlatformConfigGatewayImpl implements PlatformConfigGateway {

    private static final PlatformConfigInfraMapper MAPPER = PlatformConfigInfraMapper.INSTANCE;

    private final PlatformConfigJpaRepository platformConfigJpaRepository;

    public PlatformConfigGatewayImpl(final PlatformConfigJpaRepository platformConfigJpaRepository) {
        this.platformConfigJpaRepository = platformConfigJpaRepository;
    }

    @Override
    public Optional<PlatformConfig> findFirst() {
        return platformConfigJpaRepository.findAll()
                .stream()
                .findFirst()
                .map(MAPPER::fromEntity);
    }

    @Override
    public PlatformConfig save(final PlatformConfig platformConfig) {
        return MAPPER.fromEntity(
                platformConfigJpaRepository.save(MAPPER.toEntity(platformConfig))
        );
    }
}

