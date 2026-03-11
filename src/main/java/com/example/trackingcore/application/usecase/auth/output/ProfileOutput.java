package com.example.trackingcore.application.usecase.auth.output;

import java.util.List;

public record ProfileOutput(
        String email,
        List<String> paymentMethods,
        String establishmentName,
        String address
) {}

