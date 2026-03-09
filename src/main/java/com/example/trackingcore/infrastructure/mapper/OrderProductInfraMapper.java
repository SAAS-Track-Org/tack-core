package com.example.trackingcore.infrastructure.mapper;

import com.example.trackingcore.domain.model.OrderProduct;
import com.example.trackingcore.infrastructure.persistence.orderproduct.OrderProductEntity;
import com.example.trackingcore.infrastructure.persistence.orderproduct.OrderProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OrderInfraMapper.class, ProductInfraMapper.class})
public interface OrderProductInfraMapper {

    OrderProductInfraMapper INSTANCE = Mappers.getMapper(OrderProductInfraMapper.class);

    default OrderProduct fromEntity(final OrderProductEntity entity) {
        if (entity == null) return null;
        return OrderProduct.with(
                OrderInfraMapper.INSTANCE.fromEntity(entity.getOrder()),
                ProductInfraMapper.INSTANCE.fromEntity(entity.getProduct())
        );
    }

    @Mapping(target = "id", source = "domain", qualifiedByName = "orderProductToInfraId")
    @Mapping(target = "order", source = "domain.order")
    @Mapping(target = "product", source = "domain.product")
    OrderProductEntity toEntity(final OrderProduct domain);

    @Named("orderProductToInfraId")
    default OrderProductId orderProductToInfraId(final OrderProduct domain) {
        if (domain == null) return null;
        return new OrderProductId(domain.getOrder().getId(), domain.getProduct().getId());
    }
}
