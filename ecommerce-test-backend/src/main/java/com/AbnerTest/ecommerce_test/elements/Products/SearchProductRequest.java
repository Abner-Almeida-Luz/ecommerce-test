package com.AbnerTest.ecommerce_test.elements.Products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record SearchProductRequest(
        @NotBlank String name,
        @Positive Long categoryId,
        @Positive BigDecimal minPrice,
        @Positive BigDecimal maxPrice,
        @PositiveOrZero int page,
        @Positive int size
        ) { }