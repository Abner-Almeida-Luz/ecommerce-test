package com.AbnerTest.ecommerce_test.elements.Users;

public record LoginResponse(
        String acessToken,
        String refreshToken
) { }