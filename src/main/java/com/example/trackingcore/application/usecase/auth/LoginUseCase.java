package com.example.trackingcore.application.usecase.auth;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.auth.input.LoginInput;
import com.example.trackingcore.application.usecase.auth.output.LoginOutput;
import com.example.trackingcore.domain.exception.DomainException;
import com.example.trackingcore.domain.model.AppUser;
import com.example.trackingcore.domain.model.PaymentMethod;
import com.example.trackingcore.domain.model.PlatformConfig;
import com.example.trackingcore.domain.port.AppUserGateway;
import com.example.trackingcore.domain.port.PaymentMethodGateway;
import com.example.trackingcore.domain.port.PlatformConfigGateway;
import com.example.trackingcore.infrastructure.config.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoginUseCase extends UseCase<LoginInput, LoginOutput> {

    private static final int DEFAULT_SESSION_DURATION_HOURS = 8;

    private final AppUserGateway appUserGateway;
    private final PaymentMethodGateway paymentMethodGateway;
    private final PlatformConfigGateway platformConfigGateway;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginUseCase(
            final AppUserGateway appUserGateway,
            final PaymentMethodGateway paymentMethodGateway,
            final PlatformConfigGateway platformConfigGateway,
            final JwtTokenProvider jwtTokenProvider
    ) {
        this.appUserGateway = appUserGateway;
        this.paymentMethodGateway = paymentMethodGateway;
        this.platformConfigGateway = platformConfigGateway;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public LoginOutput execute(final LoginInput input) {
        // 1. Validate and resolve payment methods
        final List<PaymentMethod> resolvedMethods = resolvePaymentMethods(input.paymentMethodIds());

        // 2. Find or create AppUser, always updating payment methods
        final AppUser appUser = appUserGateway.findByEmail(input.email())
                .map(existing -> appUserGateway.save(existing.withPaymentMethods(resolvedMethods)))
                .orElseGet(() -> appUserGateway.save(AppUser.create(input.email(), resolvedMethods)));

        // 3. Read session duration from platform config
        final int sessionDurationHours = platformConfigGateway.findFirst()
                .map(PlatformConfig::getSessionDurationHours)
                .orElse(DEFAULT_SESSION_DURATION_HOURS);

        // 4. Generate JWT
        final Instant expiresAt = Instant.now().plus(sessionDurationHours, ChronoUnit.HOURS);
        final String token = jwtTokenProvider.generateToken(appUser.getEmail(), appUser.getId(), expiresAt);

        return new LoginOutput(token, appUser.getEmail(), sessionDurationHours, expiresAt);
    }

    private List<PaymentMethod> resolvePaymentMethods(final List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new DomainException("At least one payment method must be provided");
        }

        final List<PaymentMethod> found = paymentMethodGateway.findAllByIds(ids);

        if (found.size() != ids.size()) {
            final List<String> foundIds = found.stream().map(PaymentMethod::getId).toList();
            final List<String> invalidIds = ids.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new DomainException("Invalid payment method(s): " + invalidIds);
        }

        return found;
    }
}
