package com.example.trackingcore.domain.port;

import com.example.trackingcore.domain.model.AppUser;

import java.util.Optional;

public interface AppUserGateway {

    Optional<AppUser> findByEmail(String email);

    AppUser save(AppUser appUser);
}
