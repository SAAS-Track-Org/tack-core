package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.order.response.StandbyOrderResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/order")
@Tag(name = "Order API", description = "API for managing orders")
public interface OrderApi {

    @GetMapping(value = "/standby", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<StandbyOrderResponse> listStandby();
}

