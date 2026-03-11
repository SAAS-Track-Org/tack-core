package com.example.trackingcore.application.usecase.auth;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.auth.input.UpdateProfileInput;
import com.example.trackingcore.application.usecase.auth.output.ProfileOutput;
import com.example.trackingcore.domain.exception.DomainException;
import com.example.trackingcore.domain.exception.NotFoundException;
import com.example.trackingcore.domain.model.PaymentMethod;
import com.example.trackingcore.domain.port.AppUserGateway;
import com.example.trackingcore.domain.port.PaymentMethodGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateProfileUseCase extends UseCase<UpdateProfileInput, ProfileOutput> {

    private final AppUserGateway appUserGateway;
    private final PaymentMethodGateway paymentMethodGateway;

    public UpdateProfileUseCase(
            final AppUserGateway appUserGateway,
            final PaymentMethodGateway paymentMethodGateway
    ) {
        this.appUserGateway = appUserGateway;
        this.paymentMethodGateway = paymentMethodGateway;
    }

    @Override
    @Transactional
    public ProfileOutput execute(final UpdateProfileInput input) {
        if (input.paymentMethodIds() == null || input.paymentMethodIds().isEmpty()) {
            throw new DomainException("At least one payment method must be provided");
        }

        final var user = appUserGateway.findById(input.appUserId())
                .orElseThrow(() -> NotFoundException.of("AppUser", input.appUserId()));

        final List<PaymentMethod> resolved = resolvePaymentMethods(input.paymentMethodIds());

        final var updated = appUserGateway.save(
                user.updateProfile(resolved, input.establishmentName(), input.address())
        );

        final var methodIds = updated.getPaymentMethods().stream()
                .map(PaymentMethod::getId)
                .toList();

        return new ProfileOutput(
                updated.getEmail(),
                methodIds,
                updated.getEstablishmentName(),
                updated.getAddress()
        );
    }

    private List<PaymentMethod> resolvePaymentMethods(final List<String> ids) {
        final List<PaymentMethod> found = paymentMethodGateway.findAllByIds(ids);
        if (found.size() != ids.size()) {
            final List<String> foundIds = found.stream().map(PaymentMethod::getId).toList();
            final List<String> invalid = ids.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new DomainException("Invalid payment method(s): " + invalid);
        }
        return found;
    }
}

