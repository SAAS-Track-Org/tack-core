package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.address.response.GetAddressByIdResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/address")
@Tag(name = "Address API", description = "API for managing addresses")
public interface AddressApi {

    @GetMapping(value = "/{addressId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    GetAddressByIdResponse getById(@PathVariable UUID addressId);
}

