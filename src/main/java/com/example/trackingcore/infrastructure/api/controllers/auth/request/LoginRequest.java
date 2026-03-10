package com.example.trackingcore.infrastructure.api.controllers.auth.request;

import java.util.List;

public record LoginRequest(String email, List<String> paymentMethods) {
}
