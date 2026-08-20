package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Request to create a new user")
public record RegisterRequest(
        @Schema(description = "User name", example = "joao")
        @NotBlank String username,
        @Schema(description = "User email login", example = "joao@gmail.com")
        @NotBlank String login,
        @Schema(description = "User password", example = "password123")
        @NotBlank String password,
        @Schema(description = "User role", example = "USER")
        @NotNull UserRole role
) { }
