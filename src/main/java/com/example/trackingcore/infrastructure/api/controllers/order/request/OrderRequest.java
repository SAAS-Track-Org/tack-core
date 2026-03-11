package com.example.trackingcore.infrastructure.api.controllers.order.request;

import com.example.trackingcore.infrastructure.api.controllers.client.request.ClientRequest;

public record OrderRequest(
        ClientRequest client,
        String notes
) {}