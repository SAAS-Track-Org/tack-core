package com.example.trackingcore.infrastructure.api.controllers.auth.response;

import java.time.Instant;

public record LoginResponse(
        String token,
        String email,
        int sessionDurationHours,
        Instant expiresAt
) {
}

