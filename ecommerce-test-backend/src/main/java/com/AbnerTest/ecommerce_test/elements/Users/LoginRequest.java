package com.AbnerTest.ecommerce_test.elements.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "Request to login")
public record LoginRequest(
        @Schema(description = "Login email", example = "joao@gmail.com")
        @NotBlank String login,
        @Schema(description = "Password", example = "password123")
        @NotBlank String password
){ }