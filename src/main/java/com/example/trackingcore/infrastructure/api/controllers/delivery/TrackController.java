package com.example.trackingcore.infrastructure.api.controllers.delivery;

import com.example.trackingcore.application.usecase.delivery.GetClientTrackUseCase;
import com.example.trackingcore.application.usecase.delivery.UpdateClientTrackUseCase;
import com.example.trackingcore.infrastructure.api.TrackApi;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateClientTrackRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.ClientTrackResponse;
import com.example.trackingcore.infrastructure.mapper.TrackInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TrackController implements TrackApi {

    private final GetClientTrackUseCase getClientTrackUseCase;
    private final UpdateClientTrackUseCase updateClientTrackUseCase;

    private static final TrackInfraMapper MAPPER = TrackInfraMapper.INSTANCE;

    public TrackController(
            final GetClientTrackUseCase getClientTrackUseCase,
            final UpdateClientTrackUseCase updateClientTrackUseCase
    ) {
        this.getClientTrackUseCase = getClientTrackUseCase;
        this.updateClientTrackUseCase = updateClientTrackUseCase;
    }

    @Override
    public ClientTrackResponse getOrderTrack(final UUID publicCodeClient, final String orderCode) {
        return MAPPER.toClientTrackResponse(
                getClientTrackUseCase.execute(MAPPER.toClientTrackInput(publicCodeClient, orderCode))
        );
    }

    @Override
    public ClientTrackResponse updateOrderTrack(
            final UUID publicCodeClient,
            final String orderCode,
            final UpdateClientTrackRequest request
    ) {
        return MAPPER.toClientTrackResponse(
                updateClientTrackUseCase.execute(MAPPER.toUpdateClientTrackInput(publicCodeClient, orderCode, request))
        );
    }
}
