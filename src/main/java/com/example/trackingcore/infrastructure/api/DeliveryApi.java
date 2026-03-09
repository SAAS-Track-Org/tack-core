package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateDeliverymanRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateLocationRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateOrderRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateOrderStatusRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.AddOrderResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.CreateDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliveryDetailResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliverySummaryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DriverDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.TrackDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.UpdateLocationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/delivery")
@Tag(name = "Delivery API", description = "API for managing deliveries")
@Validated
public interface DeliveryApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<DeliverySummaryResponse> getAllDeliveries();

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    CreateDeliveryResponse createDelivery();

    @GetMapping(value = "/{deliveryId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DeliveryDetailResponse getDeliveryDetail(@PathVariable UUID deliveryId);

    @PostMapping(value = "/{deliveryId}/order", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AddOrderResponse addOrder(@PathVariable UUID deliveryId);

    @PutMapping(value = "/{deliveryId}/order/{orderCode}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DeliveryDetailResponse.OrderDetail updateOrder(
            @PathVariable UUID deliveryId,
            @PathVariable String orderCode,
            @RequestBody UpdateOrderRequest request
    );

    @PatchMapping(value = "/{deliveryId}/order/{orderCode}/status", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DeliveryDetailResponse.OrderDetail updateOrderStatus(
            @PathVariable UUID deliveryId,
            @PathVariable String orderCode,
            @Valid @RequestBody UpdateOrderStatusRequest request
    );

    @PatchMapping(value = "/{deliveryId}/order/{orderCode}/link", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DeliveryDetailResponse.OrderDetail linkOrder(
            @PathVariable UUID deliveryId,
            @PathVariable String orderCode
    );

    @PutMapping(value = "/{deliveryId}/deliveryman", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DeliveryDetailResponse updateDeliveryman(
            @PathVariable UUID deliveryId,
            @RequestBody UpdateDeliverymanRequest request
    );

    @GetMapping("/track/{publicCodeClient}/{orderCode}")
    @ResponseStatus(HttpStatus.OK)
    TrackDeliveryResponse trackDelivery(@PathVariable UUID publicCodeClient, @PathVariable String orderCode);

    @GetMapping("/driver/{publicCodeDeliveryman}")
    @ResponseStatus(HttpStatus.OK)
    DriverDeliveryResponse trackDeliveryByDriver(@PathVariable UUID publicCodeDeliveryman);

    @PatchMapping(value = "/{publicCodeDeliveryman}/location", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UpdateLocationResponse updateLocation(@PathVariable UUID publicCodeDeliveryman, @RequestBody UpdateLocationRequest request);

}
