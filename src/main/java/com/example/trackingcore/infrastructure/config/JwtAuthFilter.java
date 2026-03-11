package com.example.trackingcore.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull final HttpServletRequest request) {
        final String path = request.getRequestURI();
        final String method = request.getMethod();

        return (method.equals("POST") && path.equals("/api/v1/auth/login"))
                || (method.equals("GET") && path.equals("/api/v1/auth/config"))
                || (method.equals("GET") && path.startsWith("/api/v1/delivery/track/"))
                || (method.equals("GET") && path.startsWith("/api/v1/delivery/driver/"))
                || (method.equals("GET") && path.startsWith("/api/v1/track/"))
                || (method.equals("PATCH") && path.matches("/api/v1/delivery/.*/location"))
                || path.startsWith("/api/v1/ws")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            writeUnauthorized(response, "Token ausente ou formato inválido");
            return;
        }

        final String token = authHeader.substring(BEARER_PREFIX.length());

        if (!jwtTokenProvider.isTokenValid(token)) {
            writeUnauthorized(response, "Token inválido ou expirado");
            return;
        }

        final var claims = jwtTokenProvider.parseToken(token);
        final var subject = claims.getSubject();

        final var authentication = new UsernamePasswordAuthenticationToken(
                subject, null, List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void writeUnauthorized(final HttpServletResponse response, final String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        final var body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", message,
                "timestamp", LocalDateTime.now().toString()
        );

        OBJECT_MAPPER.writeValue(response.getWriter(), body);
    }
}
