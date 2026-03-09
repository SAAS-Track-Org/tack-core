package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.Product;
import com.example.trackingcore.domain.port.ProductGateway;
import com.example.trackingcore.infrastructure.mapper.ProductInfraMapper;
import com.example.trackingcore.infrastructure.persistence.product.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductGatewayImpl implements ProductGateway {

    private final ProductRepository productRepository;

    private static final ProductInfraMapper PRODUCT_MAPPER = ProductInfraMapper.INSTANCE;

    public ProductGatewayImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id)
                .map(entity -> Product.with(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getPrice()
                ));
    }

    @Override
    public Product save(Product product) {
        return PRODUCT_MAPPER
                .fromEntity(productRepository.save(PRODUCT_MAPPER.toEntity(product)));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(PRODUCT_MAPPER::fromEntity)
                .toList();
    }
}
