package com.example.trackingcore.infrastructure.api.controllers.order.request;

import com.example.trackingcore.infrastructure.api.controllers.client.request.ClientRequest;
import com.example.trackingcore.infrastructure.api.controllers.product.request.ProductRequest;

import java.util.List;

public record OrderRequest(
        ClientRequest client,
        String notes,
        List<ProductRequest> products
) {}