package com.example.trackingcore.infrastructure.api.controllers.auth.request;

import java.util.List;

public record UpdateProfileRequest(
        List<String> paymentMethods,
        String establishmentName,
        String address
) {}

