package com.AbnerTest.ecommerce_test.elements.Products;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long productId,
        String name,
        BigDecimal price,
        Integer stock,
        String imageUrl,
        String categoryName
) { }