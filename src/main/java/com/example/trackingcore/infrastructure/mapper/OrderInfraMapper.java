package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.delivery.output.CreateDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.DriverDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.GetAllDeliveriesOutput;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.AddressStatus;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.CreateDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliverySummaryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DriverDeliveryResponse;
import com.example.trackingcore.infrastructure.persistence.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientInfraMapper.class, AddressInfraMapper.class, ProductInfraMapper.class})
public interface OrderInfraMapper {

    OrderInfraMapper INSTANCE = Mappers.getMapper(OrderInfraMapper.class);

    // --- persistence ---

    default Order fromEntity(final OrderEntity entity) {
        if (entity == null) return null;
        return Order.with(
                entity.getId(),
                entity.getDeliveryId(),
                entity.getCode(),
                ClientInfraMapper.INSTANCE.fromEntity(entity.getClient()),
                AddressInfraMapper.INSTANCE.fromEntity(entity.getDeliveryAddress()),
                entity.getNotes(),
                AddressStatus.valueOf(entity.getAddressStatus()),
                entity.getStatusOrder() != null
                        ? OrderStatus.valueOf(entity.getStatusOrder())
                        : OrderStatus.WAITING,
                entity.getTotalAmount(),
                entity.getPaymentMethod() != null ? PaymentMethod.valueOf(entity.getPaymentMethod()) : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Mapping(target = "id",              source = "domain.id")
    @Mapping(target = "code",            source = "domain.code")
    @Mapping(target = "deliveryId",      ignore = true)
    @Mapping(target = "client",          source = "domain.client")
    @Mapping(target = "deliveryAddress", source = "domain.deliveryAddress")
    @Mapping(target = "notes",           source = "domain.notes")
    @Mapping(target = "addressStatus",   expression = "java(domain.getStatus().name())")
    @Mapping(target = "statusOrder",     expression = "java(domain.getDeliveryStatus().name())")
    @Mapping(target = "totalAmount",     source = "domain.totalAmount")
    @Mapping(target = "paymentMethod",   expression = "java(domain.getPaymentMethod() != null ? domain.getPaymentMethod().name() : null)")
    @Mapping(target = "createdAt",       source = "domain.createdAt")
    @Mapping(target = "updatedAt",       source = "domain.updatedAt")
    OrderEntity toEntity(Order domain);

    // --- output → response ---


    @Mapping(target = "stausAddress", source = "addressStatus")
    DeliverySummaryResponse.Order toSummaryOrder(GetAllDeliveriesOutput.OrderOutput order);

    DriverDeliveryResponse.OrderResponse toDriverOrderResponse(DriverDeliveryOutput.OrderOutput order);

    CreateDeliveryResponse.OrderResponse toCreateOrderResponse(CreateDeliveryOutput.OrderOutput order);
}
