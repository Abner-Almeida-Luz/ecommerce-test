package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String username,
        String login,
        UserRole role,
        LocalDateTime created_at
) { }
