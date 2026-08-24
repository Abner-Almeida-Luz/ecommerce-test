package com.AbnerTest.ecommerce_test.elements.Orders;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long orderItemId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal total
) { }