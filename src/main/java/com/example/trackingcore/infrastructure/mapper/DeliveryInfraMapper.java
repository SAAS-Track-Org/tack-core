package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.delivery.input.UpdateOrderInput;
import com.example.trackingcore.application.usecase.delivery.output.CreateDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.application.usecase.delivery.output.DriverDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.GetAllDeliveriesOutput;
import com.example.trackingcore.application.usecase.delivery.output.TrackDeliveryOutput;
import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.model.enums.DeliveryStatus;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateOrderRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.AddOrderResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.CreateDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliveryDetailResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliverySummaryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DriverDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.TrackDeliveryResponse;
import com.example.trackingcore.infrastructure.persistence.delivery.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(uses = {DeliveryManInfraMapper.class, OrderInfraMapper.class, AddressInfraMapper.class})
public interface DeliveryInfraMapper {

    DeliveryInfraMapper INSTANCE = Mappers.getMapper(DeliveryInfraMapper.class);

    // --- create delivery ---

    CreateDeliveryResponse toCreateDeliveryResponse(CreateDeliveryOutput output);

    // --- driver delivery ---

    DriverDeliveryResponse toDriverDeliveryResponse(DriverDeliveryOutput output);

    // --- delivery detail ---

    DeliveryDetailResponse.OrderDetail toOrderDetail(DeliveryDetailOutput.OrderDetailOutput order);

    @Mapping(target = "createdAt", expression = "java(output.createdAt() != null ? output.createdAt().toString() : null)")
    DeliverySummaryResponse toDeliverySummaryResponse(GetAllDeliveriesOutput output);

    // --- delivery detail ---


    @Mapping(target = "createdAt", expression = "java(output.createdAt() != null ? output.createdAt().toString() : null)")
    DeliveryDetailResponse toDeliveryDetailResponse(DeliveryDetailOutput output);

    // --- add order ---

    AddOrderResponse toAddOrderResponse(DeliveryDetailOutput.OrderDetailOutput order);

    // --- track delivery ---

    TrackDeliveryResponse toTrackDeliveryResponse(TrackDeliveryOutput output);

    // --- update order input ---

    @Mapping(target = "street",        source = "street")
    @Mapping(target = "number",        source = "number")
    @Mapping(target = "complement",    source = "complement")
    @Mapping(target = "neighborhood",  source = "neighborhood")
    @Mapping(target = "city",          source = "city")
    @Mapping(target = "state",         source = "state")
    @Mapping(target = "zipCode",       source = "zipCode")
    @Mapping(target = "country",       source = "country")
    UpdateOrderInput.AddressInput toAddressInput(UpdateOrderRequest.AddressDetail address);

    @Mapping(target = "deliveryId",    source = "deliveryId")
    @Mapping(target = "orderCode",     source = "orderCode")
    @Mapping(target = "clientName",    source = "request.clientName")
    @Mapping(target = "clientPhone",   source = "request.clientPhone")
    @Mapping(target = "notes",         source = "request.notes")
    @Mapping(target = "totalAmount",   source = "request.totalAmount")
    @Mapping(target = "paymentMethod", source = "request.paymentMethod")
    @Mapping(target = "address",       source = "request.address")
    UpdateOrderInput toUpdateOrderInput(UUID deliveryId, String orderCode, UpdateOrderRequest request);

    // --- persistence ---

    default Delivery fromEntity(final DeliveryEntity entity) {
        if (entity == null) return null;
        return Delivery.with(
                entity.getId(),
                entity.getPublicCodeClient(),
                entity.getPublicCodeDeliveryman(),
                DeliveryStatus.valueOf(entity.getStatus()),
                entity.getDeliveryman() != null ? DeliveryManInfraMapper.INSTANCE.fromEntity(entity.getDeliveryman()) : null,
                entity.getOrders().stream().map(OrderInfraMapper.INSTANCE::fromEntity).toList(),
                entity.getCurrentLat(),
                entity.getCurrentLng(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeliveredAt()
        );
    }

    @Mapping(target = "status",      expression = "java(domain.getStatus().name())")
    @Mapping(target = "deliveryman", source = "deliveryman")
    @Mapping(target = "orders",      source = "orders")
    DeliveryEntity toEntity(Delivery domain);
}
