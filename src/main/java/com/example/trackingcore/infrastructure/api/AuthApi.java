package com.example.trackingcore.infrastructure.api;

import com.example.trackingcore.infrastructure.api.controllers.auth.request.LoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/auth")
@Tag(name = "Auth API", description = "API for authentication and platform configuration")
public interface AuthApi {

    @GetMapping(value = "/config")
    @ResponseStatus(HttpStatus.OK)
    void getConfig(HttpServletResponse response);

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void login(@RequestBody LoginRequest request, HttpServletResponse response);
}
