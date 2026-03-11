package com.example.trackingcore.infrastructure.persistence;

import com.example.trackingcore.domain.model.AppUser;
import com.example.trackingcore.domain.port.AppUserGateway;
import com.example.trackingcore.infrastructure.mapper.AppUserInfraMapper;
import com.example.trackingcore.infrastructure.persistence.appuser.AppUserJpaEntity;
import com.example.trackingcore.infrastructure.persistence.appuser.AppUserJpaRepository;
import com.example.trackingcore.infrastructure.persistence.paymentmethod.PaymentMethodJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AppUserGatewayImpl implements AppUserGateway {

    private static final AppUserInfraMapper MAPPER = AppUserInfraMapper.INSTANCE;

    private final AppUserJpaRepository appUserJpaRepository;

    public AppUserGatewayImpl(final AppUserJpaRepository appUserJpaRepository) {
        this.appUserJpaRepository = appUserJpaRepository;
    }

    @Override
    public Optional<AppUser> findByEmail(final String email) {
        return appUserJpaRepository.findByEmail(email)
                .map(MAPPER::fromEntity);
    }

    @Override
    public Optional<AppUser> findById(final UUID id) {
        return appUserJpaRepository.findById(id)
                .map(MAPPER::fromEntity);
    }

    @Override
    public AppUser save(final AppUser appUser) {
        // If entity already exists, load the managed instance and replace the collection
        // so that JPA generates the correct DELETE+INSERT on the join table.
        final AppUserJpaEntity entity = appUserJpaRepository.findById(appUser.getId())
                .map(managed -> {
                    final var incoming = MAPPER.toEntity(appUser);
                    managed.setEmail(incoming.getEmail());
                    managed.setEstablishmentName(incoming.getEstablishmentName());
                    managed.setAddress(incoming.getAddress());
                    managed.getPaymentMethods().clear();
                    managed.getPaymentMethods().addAll(
                            incoming.getPaymentMethods().stream()
                                    .map(pm -> new PaymentMethodJpaEntity(pm.getId(), pm.getLabel(), pm.getCreatedAt()))
                                    .toList()
                    );
                    return managed;
                })
                .orElseGet(() -> MAPPER.toEntity(appUser));

        return MAPPER.fromEntity(appUserJpaRepository.save(entity));
    }
}
