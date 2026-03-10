package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.domain.model.PlatformConfig;
import com.example.trackingcore.infrastructure.persistence.platformconfig.PlatformConfigJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlatformConfigInfraMapper {

    PlatformConfigInfraMapper INSTANCE = Mappers.getMapper(PlatformConfigInfraMapper.class);

    default PlatformConfig fromEntity(final PlatformConfigJpaEntity entity) {
        if (entity == null) return null;
        return PlatformConfig.with(
                entity.getId(),
                entity.getSessionDurationHours(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    default PlatformConfigJpaEntity toEntity(final PlatformConfig domain) {
        if (domain == null) return null;
        return new PlatformConfigJpaEntity(
                domain.getId(),
                domain.getSessionDurationHours(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}

