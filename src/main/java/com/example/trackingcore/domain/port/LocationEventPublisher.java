package com.example.trackingcore.domain.port;

import java.math.BigDecimal;
import java.util.UUID;

public interface LocationEventPublisher {

    void publish(UUID publicCodeClient, BigDecimal lat, BigDecimal lng);
}

