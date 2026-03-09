package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.application.usecase.product.output.GetAllProductsOutput;
import com.example.trackingcore.domain.model.Product;
import com.example.trackingcore.infrastructure.api.controllers.product.response.ProductResponse;
import com.example.trackingcore.infrastructure.persistence.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductInfraMapper {

    ProductInfraMapper INSTANCE = Mappers.getMapper(ProductInfraMapper.class);

    default Product fromEntity(final ProductEntity entity) {
        if (entity == null) return null;
        return Product.with(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice()
        );
    }

    @Mapping(target = "id",          source = "id")
    @Mapping(target = "name",        source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price",       source = "price")
    ProductEntity toEntity(final Product domain);

    ProductResponse toProductResponse(final GetAllProductsOutput output);
}
