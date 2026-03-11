package com.example.trackingcore.application.usecase.auth.input;

import java.util.List;

public record UpdateProfileInput(
        java.util.UUID appUserId,
        List<String> paymentMethodIds,
        String establishmentName,
        String address
) {}

