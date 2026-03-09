package com.example.trackingcore.application.mapper;

import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.application.usecase.delivery.output.DriverDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.GetAllDeliveriesOutput;
import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeliveryOutputMapper {

    DeliveryOutputMapper INSTANCE = Mappers.getMapper(DeliveryOutputMapper.class);

    // --- Address ---

    DeliveryDetailOutput.AddressDetailOutput toAddressDetailOutput(Address address);

    DriverDeliveryOutput.AddressOutput toDriverAddressOutput(Address address);

    // --- Order ---

    @Mapping(target = "clientName",    source = "client.name")
    @Mapping(target = "clientPhone",   source = "client.phoneNumber")
    @Mapping(target = "addressStatus", source = "status")
    @Mapping(target = "address",       source = "deliveryAddress")
    DeliveryDetailOutput.OrderDetailOutput toOrderDetailOutput(Order order);

    @Mapping(target = "addressStatus", source = "status")
    GetAllDeliveriesOutput.OrderOutput toSummaryOrderOutput(Order order);

    @Mapping(target = "orderCode",   source = "code")
    @Mapping(target = "clientName",  source = "client.name")
    @Mapping(target = "clientPhone", source = "client.phoneNumber")
    @Mapping(target = "address",     source = "deliveryAddress")
    DriverDeliveryOutput.OrderOutput toDriverOrderOutput(Order order);

    // --- Delivery ---

    default GetAllDeliveriesOutput toGetAllDeliveriesOutput(final Delivery delivery) {
        final List<Order> orders = delivery.getOrders();

        final var orderOutputs = orders.stream()
                .filter(o -> o.getDeliveryStatus() != OrderStatus.DELETED
                        && o.getDeliveryStatus() != OrderStatus.STANDBY)
                .map(this::toSummaryOrderOutput)
                .toList();

        final var clientName = orders.isEmpty() ? null
                : orders.getFirst().getClient().getName();

        final var deliverymanName = delivery.getDeliveryman() != null
                ? delivery.getDeliveryman().getName()
                : null;

        return new GetAllDeliveriesOutput(
                delivery.getId(),
                delivery.getStatus(),
                orderOutputs,
                clientName,
                deliverymanName,
                delivery.getCreatedAt()
        );
    }
}
