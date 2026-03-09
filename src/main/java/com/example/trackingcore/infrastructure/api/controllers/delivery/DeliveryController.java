package com.example.trackingcore.infrastructure.api.controllers.delivery;

import com.example.trackingcore.application.usecase.delivery.AddOrderToDeliveryUseCase;
import com.example.trackingcore.application.usecase.delivery.CreateDeliveryUseCase;
import com.example.trackingcore.application.usecase.delivery.DriverDeliveryUseCase;
import com.example.trackingcore.application.usecase.delivery.GetAllDeliveriesUseCase;
import com.example.trackingcore.application.usecase.delivery.GetDeliveryDetailUseCase;
import com.example.trackingcore.application.usecase.delivery.TrackDeliveryUseCase;
import com.example.trackingcore.application.usecase.delivery.LinkOrderToDeliveryUseCase;
import com.example.trackingcore.application.usecase.delivery.UpdateDeliverymanUseCase;
import com.example.trackingcore.application.usecase.delivery.UpdateLocationUseCase;
import com.example.trackingcore.application.usecase.delivery.UpdateOrderStatusUseCase;
import com.example.trackingcore.application.usecase.delivery.UpdateOrderUseCase;
import com.example.trackingcore.application.usecase.delivery.input.DriverDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.input.LinkOrderToDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.input.TrackDeliveryInput;
import com.example.trackingcore.application.usecase.delivery.input.UpdateDeliverymanInput;
import com.example.trackingcore.application.usecase.delivery.input.UpdateLocationInput;
import com.example.trackingcore.application.usecase.delivery.input.UpdateOrderStatusInput;
import com.example.trackingcore.infrastructure.api.DeliveryApi;
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
import com.example.trackingcore.infrastructure.mapper.DeliveryInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DeliveryController implements DeliveryApi {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final GetAllDeliveriesUseCase getAllDeliveriesUseCase;
    private final GetDeliveryDetailUseCase getDeliveryDetailUseCase;
    private final AddOrderToDeliveryUseCase addOrderToDeliveryUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final UpdateDeliverymanUseCase updateDeliverymanUseCase;
    private final LinkOrderToDeliveryUseCase linkOrderToDeliveryUseCase;
    private final TrackDeliveryUseCase trackDeliveryUseCase;
    private final DriverDeliveryUseCase driverDeliveryUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;

    private static final DeliveryInfraMapper DELIVERY_INFRA_MAPPER = DeliveryInfraMapper.INSTANCE;

    public DeliveryController(
            final CreateDeliveryUseCase createDeliveryUseCase,
            final GetAllDeliveriesUseCase getAllDeliveriesUseCase,
            final GetDeliveryDetailUseCase getDeliveryDetailUseCase,
            final AddOrderToDeliveryUseCase addOrderToDeliveryUseCase,
            final UpdateOrderUseCase updateOrderUseCase,
            final UpdateOrderStatusUseCase updateOrderStatusUseCase,
            final UpdateDeliverymanUseCase updateDeliverymanUseCase,
            final LinkOrderToDeliveryUseCase linkOrderToDeliveryUseCase,
            final TrackDeliveryUseCase trackDeliveryUseCase,
            final DriverDeliveryUseCase driverDeliveryUseCase,
            final UpdateLocationUseCase updateLocationUseCase
    ) {
        this.createDeliveryUseCase = createDeliveryUseCase;
        this.getAllDeliveriesUseCase = getAllDeliveriesUseCase;
        this.getDeliveryDetailUseCase = getDeliveryDetailUseCase;
        this.addOrderToDeliveryUseCase = addOrderToDeliveryUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.updateDeliverymanUseCase = updateDeliverymanUseCase;
        this.linkOrderToDeliveryUseCase = linkOrderToDeliveryUseCase;
        this.trackDeliveryUseCase = trackDeliveryUseCase;
        this.driverDeliveryUseCase = driverDeliveryUseCase;
        this.updateLocationUseCase = updateLocationUseCase;
    }

    @Override
    public List<DeliverySummaryResponse> getAllDeliveries() {
        return getAllDeliveriesUseCase.execute(null).stream()
                .map(DELIVERY_INFRA_MAPPER::toDeliverySummaryResponse)
                .toList();
    }

    @Override
    public CreateDeliveryResponse createDelivery() {
        final var output = createDeliveryUseCase.execute(null);
        return DELIVERY_INFRA_MAPPER.toCreateDeliveryResponse(output);
    }

    @Override
    public DeliveryDetailResponse getDeliveryDetail(final UUID deliveryId) {
        final var output = getDeliveryDetailUseCase.execute(deliveryId);
        return DELIVERY_INFRA_MAPPER.toDeliveryDetailResponse(output);
    }

    @Override
    public AddOrderResponse addOrder(final UUID deliveryId) {
        final var output = addOrderToDeliveryUseCase.execute(deliveryId);
        return DELIVERY_INFRA_MAPPER.toAddOrderResponse(output);
    }

    @Override
    public DeliveryDetailResponse.OrderDetail updateOrder(
            final UUID deliveryId,
            final String orderCode,
            final UpdateOrderRequest request
    ) {
        final var input = DELIVERY_INFRA_MAPPER.toUpdateOrderInput(deliveryId, orderCode, request);
        return DELIVERY_INFRA_MAPPER.toOrderDetail(updateOrderUseCase.execute(input));
    }

    @Override
    public DeliveryDetailResponse.OrderDetail updateOrderStatus(
            final UUID deliveryId,
            final String orderCode,
            final UpdateOrderStatusRequest request
    ) {
        final var input = new UpdateOrderStatusInput(deliveryId, orderCode, request.deliveryStatus());
        return DELIVERY_INFRA_MAPPER.toOrderDetail(updateOrderStatusUseCase.execute(input));
    }

    @Override
    public DeliveryDetailResponse updateDeliveryman(
            final UUID deliveryId,
            final UpdateDeliverymanRequest request
    ) {
        final var input = new UpdateDeliverymanInput(deliveryId, request.name(), request.phoneNumber());
        final var output = updateDeliverymanUseCase.execute(input);
        return DELIVERY_INFRA_MAPPER.toDeliveryDetailResponse(output);
    }

    @Override
    public DeliveryDetailResponse.OrderDetail linkOrder(
            final UUID deliveryId,
            final String orderCode
    ) {
        final var input = new LinkOrderToDeliveryInput(deliveryId, orderCode);
        return DELIVERY_INFRA_MAPPER.toOrderDetail(linkOrderToDeliveryUseCase.execute(input));
    }

    @Override
    public TrackDeliveryResponse trackDelivery(final UUID publicCodeClient, final String orderCode) {
        final var input = new TrackDeliveryInput(publicCodeClient, orderCode);
        final var output = trackDeliveryUseCase.execute(input);
        return DELIVERY_INFRA_MAPPER.toTrackDeliveryResponse(output);
    }

    @Override
    public DriverDeliveryResponse trackDeliveryByDriver(final UUID publicCodeDeliveryman) {
        final var input = new DriverDeliveryInput(publicCodeDeliveryman);
        final var output = driverDeliveryUseCase.execute(input);
        return DELIVERY_INFRA_MAPPER.toDriverDeliveryResponse(output);
    }

    @Override
    public UpdateLocationResponse updateLocation(final UUID publicCodeDeliveryman, final UpdateLocationRequest request) {
        final var input = new UpdateLocationInput(publicCodeDeliveryman, request.lat(), request.lng());
        final var output = updateLocationUseCase.execute(input);
        return new UpdateLocationResponse(output.lat(), output.lng());
    }
}

