package com.example.trackingcore.infrastructure.api.controllers.auth;

import com.example.trackingcore.application.usecase.auth.GetAuthConfigUseCase;
import com.example.trackingcore.application.usecase.auth.LoginUseCase;
import com.example.trackingcore.application.usecase.auth.input.LoginInput;
import com.example.trackingcore.infrastructure.api.AuthApi;
import com.example.trackingcore.infrastructure.api.controllers.auth.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private static final String HEADER_AUTHORIZATION      = "Authorization";
    private static final String HEADER_SESSION_DURATION   = "X-Session-Duration-Hours";
    private static final String HEADER_EXPIRES_AT         = "X-Expires-At";

    private final GetAuthConfigUseCase getAuthConfigUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(
            final GetAuthConfigUseCase getAuthConfigUseCase,
            final LoginUseCase loginUseCase
    ) {
        this.getAuthConfigUseCase = getAuthConfigUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public void getConfig(final HttpServletResponse response) {
        final var output = getAuthConfigUseCase.execute(null);
        response.setHeader(HEADER_SESSION_DURATION, String.valueOf(output.sessionDurationHours()));
    }

    @Override
    public void login(final LoginRequest request, final HttpServletResponse response) {
        final var output = loginUseCase.execute(new LoginInput(request.email(), request.paymentMethods()));
        response.setHeader(HEADER_AUTHORIZATION,    "Bearer " + output.token());
        response.setHeader(HEADER_SESSION_DURATION, String.valueOf(output.sessionDurationHours()));
        response.setHeader(HEADER_EXPIRES_AT,       output.expiresAt().toString());
    }
}
