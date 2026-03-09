package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.client.output.SearchClientOutput;
import com.example.trackingcore.domain.model.Client;
import com.example.trackingcore.infrastructure.api.controllers.client.response.SearchClientResponse;
import com.example.trackingcore.infrastructure.persistence.client.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressInfraMapper.class})
public interface ClientInfraMapper {

    ClientInfraMapper INSTANCE = Mappers.getMapper(ClientInfraMapper.class);

    default Client fromEntity(final ClientEntity entity) {
        if (entity == null) return null;
        return Client.with(
                entity.getId(),
                entity.getName(),
                entity.getPhoneNumber(),
                entity.getCreatedAt()
        );
    }

    @Mapping(target = "id",          source = "id")
    @Mapping(target = "name",        source = "name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "createdAt",   source = "createdAt")
    ClientEntity toEntity(final Client domain);

    SearchClientResponse toSearchClientResponse(SearchClientOutput searchClientOutput);
}
