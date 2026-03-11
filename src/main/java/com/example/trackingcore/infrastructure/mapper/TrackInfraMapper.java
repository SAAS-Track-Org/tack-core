package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.delivery.input.ClientTrackInput;
import com.example.trackingcore.application.usecase.delivery.input.UpdateClientTrackInput;
import com.example.trackingcore.application.usecase.delivery.output.ClientTrackOutput;
import com.example.trackingcore.infrastructure.api.controllers.delivery.request.UpdateClientTrackRequest;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.ClientTrackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface TrackInfraMapper {

    TrackInfraMapper INSTANCE = Mappers.getMapper(TrackInfraMapper.class);

    // --- input mapping ---

    default ClientTrackInput toClientTrackInput(final UUID publicCodeClient, final String orderCode) {
        return new ClientTrackInput(publicCodeClient, orderCode);
    }

    @Mapping(target = "street",       source = "street")
    @Mapping(target = "number",       source = "number")
    @Mapping(target = "complement",   source = "complement")
    @Mapping(target = "neighborhood", source = "neighborhood")
    @Mapping(target = "city",         source = "city")
    @Mapping(target = "state",        source = "state")
    @Mapping(target = "zipCode",      source = "zipCode")
    @Mapping(target = "country",      source = "country")
    UpdateClientTrackInput.AddressInput toAddressInput(UpdateClientTrackRequest.AddressRequest request);

    @Mapping(target = "publicCodeClient", source = "publicCodeClient")
    @Mapping(target = "orderCode",        source = "orderCode")
    @Mapping(target = "clientName",       source = "request.clientName")
    @Mapping(target = "clientPhone",      source = "request.clientPhone")
    @Mapping(target = "paymentMethod",    source = "request.paymentMethod")
    @Mapping(target = "address",          source = "request.address")
    UpdateClientTrackInput toUpdateClientTrackInput(UUID publicCodeClient, String orderCode, UpdateClientTrackRequest request);

    // --- output mapping ---

    ClientTrackResponse.AddressDetail toAddressDetail(ClientTrackOutput.AddressOutput address);

    ClientTrackResponse.DeliveryInfo toDeliveryInfo(ClientTrackOutput.DeliveryInfo delivery);

    ClientTrackResponse toClientTrackResponse(ClientTrackOutput output);
}
