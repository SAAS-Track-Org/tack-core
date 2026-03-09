package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.deliveryman.request.CreateDeliveryManRequest;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.GetDeliverymanByIdResponse;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.SearchDeliverymanResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/deliveryman")
@Tag(name = "Delivery Man API", description = "API for managing delivery men")
public interface DeliveryManApi {

    @PostMapping
    void createDeliveryMan(@RequestBody CreateDeliveryManRequest request);

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SearchDeliverymanResponse> search(@RequestParam String q);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    GetDeliverymanByIdResponse getById(@PathVariable UUID id);
}


