package com.example.trackingcore.application.usecase.auth.input;

import java.util.List;

public record LoginInput(String email, List<String> paymentMethodIds) {
}
