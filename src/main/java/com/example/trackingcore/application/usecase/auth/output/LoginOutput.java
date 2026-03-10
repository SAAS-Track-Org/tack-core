package com.example.trackingcore.application.usecase.auth.output;

import java.time.Instant;

public record LoginOutput(
        String token,
        String email,
        int sessionDurationHours,
        Instant expiresAt
) {
}

