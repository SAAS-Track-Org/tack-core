package com.example.trackingcore.application.usecase.auth;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.auth.input.LoginInput;
import com.example.trackingcore.application.usecase.auth.output.LoginOutput;
import com.example.trackingcore.domain.model.AppUser;
import com.example.trackingcore.domain.model.PlatformConfig;
import com.example.trackingcore.domain.port.AppUserGateway;
import com.example.trackingcore.domain.port.PlatformConfigGateway;
import com.example.trackingcore.infrastructure.config.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class LoginUseCase extends UseCase<LoginInput, LoginOutput> {

    private static final int DEFAULT_SESSION_DURATION_HOURS = 8;

    private final AppUserGateway appUserGateway;
    private final PlatformConfigGateway platformConfigGateway;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginUseCase(
            final AppUserGateway appUserGateway,
            final PlatformConfigGateway platformConfigGateway,
            final JwtTokenProvider jwtTokenProvider
    ) {
        this.appUserGateway = appUserGateway;
        this.platformConfigGateway = platformConfigGateway;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public LoginOutput execute(final LoginInput input) {
        // Find or create AppUser (no payment methods at login time)
        final AppUser appUser = appUserGateway.findByEmail(input.email())
                .orElseGet(() -> appUserGateway.save(AppUser.create(input.email())));

        final int sessionDurationHours = platformConfigGateway.findFirst()
                .map(PlatformConfig::getSessionDurationHours)
                .orElse(DEFAULT_SESSION_DURATION_HOURS);

        final Instant expiresAt = Instant.now().plus(sessionDurationHours, ChronoUnit.HOURS);
        final String token = jwtTokenProvider.generateToken(appUser.getEmail(), appUser.getId(), expiresAt);

        return new LoginOutput(token, appUser.getEmail(), sessionDurationHours, expiresAt);
    }
}
