package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.address.output.GetAddressByIdOutput;
import com.example.trackingcore.application.usecase.client.output.ListAddressesByClientIdOutput;
import com.example.trackingcore.application.usecase.delivery.output.CreateDeliveryOutput;
import com.example.trackingcore.application.usecase.delivery.output.DeliveryDetailOutput;
import com.example.trackingcore.application.usecase.delivery.output.DriverDeliveryOutput;
import com.example.trackingcore.domain.model.Address;
import com.example.trackingcore.infrastructure.api.controllers.address.response.GetAddressByIdResponse;
import com.example.trackingcore.infrastructure.api.controllers.client.response.ListAddressesResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.CreateDeliveryResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DeliveryDetailResponse;
import com.example.trackingcore.infrastructure.api.controllers.delivery.response.DriverDeliveryResponse;
import com.example.trackingcore.infrastructure.persistence.address.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressInfraMapper {

    AddressInfraMapper INSTANCE = Mappers.getMapper(AddressInfraMapper.class);

    // --- persistence ---

    default Address fromEntity(final AddressEntity entity) {
        if (entity == null) return null;
        return Address.with(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getCountry()
        );
    }

    @Mapping(target = "id",           source = "domain.id")
    @Mapping(target = "street",       source = "domain.street")
    @Mapping(target = "number",       source = "domain.number")
    @Mapping(target = "complement",   source = "domain.complement")
    @Mapping(target = "neighborhood", source = "domain.neighborhood")
    @Mapping(target = "city",         source = "domain.city")
    @Mapping(target = "state",        source = "domain.state")
    @Mapping(target = "zipCode",      source = "domain.zipCode")
    @Mapping(target = "country",      source = "domain.country")
    AddressEntity toEntity(Address domain);

    // --- output → response ---

    GetAddressByIdResponse toGetAddressByIdResponse(GetAddressByIdOutput output);

    ListAddressesResponse toListAddressesResponse(ListAddressesByClientIdOutput output);

    DeliveryDetailResponse.AddressDetail toAddressDetail(DeliveryDetailOutput.AddressDetailOutput addr);

    DriverDeliveryResponse.AddressResponse toDriverAddressResponse(DriverDeliveryOutput.AddressOutput addr);

    CreateDeliveryResponse.Address toCreateDeliveryAddress(CreateDeliveryOutput.AddressOutput addr);
}
