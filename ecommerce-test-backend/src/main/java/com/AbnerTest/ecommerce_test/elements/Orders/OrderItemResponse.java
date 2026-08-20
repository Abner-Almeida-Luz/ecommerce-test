package com.AbnerTest.ecommerce_test.elements.Orders;

public record OrderItemResponse(
        Long orderItemId,
        Long productId,
        String productName,
        Integer quantity,
        String total
) { }