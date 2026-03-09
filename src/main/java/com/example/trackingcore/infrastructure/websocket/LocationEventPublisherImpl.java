package com.example.trackingcore.infrastructure.websocket;

import com.example.trackingcore.domain.port.LocationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class LocationEventPublisherImpl implements LocationEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LocationEventPublisherImpl(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void publish(final UUID publicCodeClient, final BigDecimal lat, final BigDecimal lng) {
        final var destination = "/topic/track/" + publicCodeClient;
        final var payload = Map.of("lat", lat, "lng", lng);
        messagingTemplate.convertAndSend(destination, payload);
    }
}
