package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.product.response.ProductResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/product")
@Tag(name = "Product API", description = "API for managing products")
public interface ProductApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ProductResponse> getAll();
}

