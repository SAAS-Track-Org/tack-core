package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateClientTrackRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.ClientTrackResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/track")
@Tag(name = "Track API", description = "Public API for client order tracking")
@Validated
public interface TrackApi {

    @GetMapping(value = "/{publicCodeClient}/{orderCode}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ClientTrackResponse getOrderTrack(
            @PathVariable UUID publicCodeClient,
            @PathVariable String orderCode
    );

    @PatchMapping(value = "/{publicCodeClient}/{orderCode}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ClientTrackResponse updateOrderTrack(
            @PathVariable UUID publicCodeClient,
            @PathVariable String orderCode,
            @Valid @RequestBody UpdateClientTrackRequest request
    );
}

