package com.example.trackingcore.application.usecase.auth;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.auth.output.ProfileOutput;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.port.AppUserGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetProfileUseCase extends UseCase<UUID, ProfileOutput> {

    private final AppUserGateway appUserGateway;

    public GetProfileUseCase(final AppUserGateway appUserGateway) {
        this.appUserGateway = appUserGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileOutput execute(final UUID appUserId) {
        final var user = appUserGateway.findById(appUserId)
                .orElseThrow(() -> NotFoundException.of("AppUser", appUserId));

        final var methodIds = user.getPaymentMethods() == null
                ? java.util.List.<String>of()
                : user.getPaymentMethods().stream()
                        .map(com.example.trackingcore.domain.model.PaymentMethod::getId)
                        .toList();

        return new ProfileOutput(
                user.getEmail(),
                methodIds,
                user.getEstablishmentName(),
                user.getAddress()
        );
    }
}

