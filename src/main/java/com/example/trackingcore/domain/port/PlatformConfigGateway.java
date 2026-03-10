package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.PlatformConfig;

import java.util.Optional;

public interface PlatformConfigGateway {

    Optional<PlatformConfig> findFirst();

    PlatformConfig save(PlatformConfig platformConfig);
}

