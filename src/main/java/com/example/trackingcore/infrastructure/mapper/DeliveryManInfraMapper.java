package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.deliveryman.output.GetDeliverymanByIdOutput;
import com.example.trackingcore.application.usecase.deliveryman.output.SearchDeliverymanOutput;
import com.example.trackingcore.domain.model.Deliveryman;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.GetDeliverymanByIdResponse;
import com.example.trackingcore.infrastructure.api.controllers.deliveryman.response.SearchDeliverymanResponse;
import com.example.trackingcore.infrastructure.persistence.deliveryman.DeliveryManEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryManInfraMapper {

    DeliveryManInfraMapper INSTANCE = Mappers.getMapper(DeliveryManInfraMapper.class);

    default Deliveryman fromEntity(final DeliveryManEntity entity) {
        if (entity == null) return null;
        return Deliveryman.with(
                entity.getId(),
                entity.getName(),
                entity.getPhoneNumber()
        );
    }

    @Mapping(target = "id",          source = "id")
    @Mapping(target = "name",        source = "name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    DeliveryManEntity toEntity(final Deliveryman domain);

    SearchDeliverymanResponse toSearchDeliverymanResponse (final SearchDeliverymanOutput output);

    GetDeliverymanByIdResponse toGetDeliverymanByIdResponse (final GetDeliverymanByIdOutput output);
}
