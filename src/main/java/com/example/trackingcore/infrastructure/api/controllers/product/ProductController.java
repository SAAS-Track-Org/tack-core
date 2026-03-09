package com.example.trackingcore.infrastructure.api.controllers.product;

import com.example.trackingcore.application.usecase.product.GetAllProductsUseCase;
import com.example.trackingcore.application.usecase.product.input.GetAllProductsInput;
import com.example.trackingcore.infrastructure.api.ProductApi;
import com.example.trackingcore.infrastructure.api.controllers.product.response.ProductResponse;
import com.example.trackingcore.infrastructure.mapper.ProductInfraMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private static final ProductInfraMapper PRODUCT_MAPPER = ProductInfraMapper.INSTANCE;

    private final GetAllProductsUseCase getAllProductsUseCase;

    public ProductController(final GetAllProductsUseCase getAllProductsUseCase) {
        this.getAllProductsUseCase = getAllProductsUseCase;
    }

    @Override
    public List<ProductResponse> getAll() {
        final var output = getAllProductsUseCase.execute(new GetAllProductsInput());
        return output.stream()
                .map(PRODUCT_MAPPER::toProductResponse)
                .toList();
    }
}
