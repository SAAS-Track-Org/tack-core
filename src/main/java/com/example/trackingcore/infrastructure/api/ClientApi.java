package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.client.response.ListAddressesResponse;
import com.example.trackingcore.infrastructure.api.controllers.client.response.SearchClientResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/client")
@Tag(name = "Client API", description = "API for managing clients")
public interface ClientApi {

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SearchClientResponse> search(@RequestParam String q);

    @GetMapping(value = "/{clientId}/addresses", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ListAddressesResponse> listAddresses(@PathVariable UUID clientId);
}
