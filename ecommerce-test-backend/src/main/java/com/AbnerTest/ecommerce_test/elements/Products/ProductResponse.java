package com.AbnerTest.ecommerce_test.elements.Products;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long productId,
        Long categoryId,
        String categoryName,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String imageUrl,
        LocalDateTime createdAt
) { }