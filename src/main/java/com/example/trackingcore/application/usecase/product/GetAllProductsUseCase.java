package com.example.trackingcore.application.usecase.product;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.product.input.GetAllProductsInput;
import com.example.trackingcore.application.usecase.product.output.GetAllProductsOutput;
import com.example.trackingcore.domain.port.ProductGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllProductsUseCase extends UseCase<GetAllProductsInput, List<GetAllProductsOutput>> {

    private final ProductGateway productGateway;

    public GetAllProductsUseCase(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAllProductsOutput> execute(final GetAllProductsInput input) {
        return productGateway.findAll()
                .stream()
                .map(product -> new GetAllProductsOutput(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .toList();
    }
}

