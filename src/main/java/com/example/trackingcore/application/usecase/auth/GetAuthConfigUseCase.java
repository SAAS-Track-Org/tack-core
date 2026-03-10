package com.example.trackingcore.application.usecase.auth;

import com.example.trackingcore.application.usecase.UseCase;
import com.example.trackingcore.application.usecase.auth.output.GetAuthConfigOutput;
import com.example.trackingcore.domain.model.PlatformConfig;
import com.example.trackingcore.domain.port.PlatformConfigGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAuthConfigUseCase extends UseCase<Void, GetAuthConfigOutput> {

    private static final int DEFAULT_SESSION_DURATION_HOURS = 8;

    private final PlatformConfigGateway platformConfigGateway;

    public GetAuthConfigUseCase(final PlatformConfigGateway platformConfigGateway) {
        this.platformConfigGateway = platformConfigGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public GetAuthConfigOutput execute(final Void input) {
        final int sessionDurationHours = platformConfigGateway.findFirst()
                .map(PlatformConfig::getSessionDurationHours)
                .orElse(DEFAULT_SESSION_DURATION_HOURS);

        return new GetAuthConfigOutput(sessionDurationHours);
    }
}

