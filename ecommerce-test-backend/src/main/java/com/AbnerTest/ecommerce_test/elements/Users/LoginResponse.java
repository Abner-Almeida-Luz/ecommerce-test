package com.AbnerTest.ecommerce_test.elements.Users;

public record LoginResponse(
        String accessToken,
        String refreshToken
) { }