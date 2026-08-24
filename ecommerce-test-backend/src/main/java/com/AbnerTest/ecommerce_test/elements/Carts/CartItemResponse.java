package com.AbnerTest.ecommerce_test.elements.Carts;

import java.math.BigDecimal;

public record CartItemResponse(
        Long cartItemId,
        Long cartId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal total,
        String productImageUrl
) { }