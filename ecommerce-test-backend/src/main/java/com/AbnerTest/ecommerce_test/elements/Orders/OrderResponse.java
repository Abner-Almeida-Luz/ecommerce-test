package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        Long userId,
        OrderStatus status,
        BigDecimal total,
        List<com.AbnerTest.ecommerce_test.core.OrderItems> orderItems,
        LocalDateTime createdAt
) { }