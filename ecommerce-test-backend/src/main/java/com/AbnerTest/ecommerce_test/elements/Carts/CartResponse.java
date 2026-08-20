package com.AbnerTest.ecommerce_test.elements.Carts;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        Long cartId,
        Long userId,
        List<CartItemResponse> cartItems,
        LocalDateTime createdAt
) { }