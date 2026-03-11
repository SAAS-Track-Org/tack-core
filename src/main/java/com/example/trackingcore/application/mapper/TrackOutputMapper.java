package com.example.trackingcore.application.mapper;

import com.example.trackingcore.application.usecase.delivery.output.ClientTrackOutput;
import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.domain.model.Delivery;
import com.example.trackingcore.domain.model.Order;
import com.example.trackingcore.domain.model.enums.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TrackOutputMapper {

    TrackOutputMapper INSTANCE = Mappers.getMapper(TrackOutputMapper.class);

    // --- Address ---

    ClientTrackOutput.AddressOutput toAddressOutput(Address address);

    // --- Delivery info ---

    @Mapping(target = "deliveryId", source = "id")
    ClientTrackOutput.DeliveryInfo toDeliveryInfo(Delivery delivery);

    // --- Full output (Order + Delivery) ---

    @Mapping(target = "orderCode",                source = "order.code")
    @Mapping(target = "clientName",               source = "order.client.name")
    @Mapping(target = "clientPhone",              source = "order.client.phoneNumber")
    @Mapping(target = "deliveryStatus",           source = "order.deliveryStatus")
    @Mapping(target = "addressStatus",            source = "order.status")
    @Mapping(target = "address",                  source = "order.deliveryAddress")
    @Mapping(target = "paymentMethod",            source = "order.paymentMethod")
    @Mapping(target = "totalAmount",              source = "order.totalAmount")
    @Mapping(target = "notes",                    source = "order.notes")
    @Mapping(target = "delivery",                 source = "delivery")
    @Mapping(target = "availablePaymentMethods",  source = "availablePaymentMethods")
    ClientTrackOutput toClientTrackOutput(Order order, Delivery delivery, List<PaymentMethod> availablePaymentMethods);
}
