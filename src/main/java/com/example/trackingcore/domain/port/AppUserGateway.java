package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserGateway {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findById(UUID id);

    AppUser save(AppUser appUser);
}
