package com.AbnerTest.ecommerce_test.elements.Products;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request to create a new product")
public record ProductRequest(
        @Schema(description = "Category id", example = "1")
        @NotNull @Positive Long categoryId,
        @Schema(description = "Product name", example = "Potato")
        @NotBlank String name,
        @Schema(description = "Product description", example = "French Potato")
        @NotBlank String description,
        @Schema(description = "Product price", example = "1")
        @NotNull @Positive BigDecimal price,
        @Schema(description = "Product stock", example = "1")
        @NotNull @Positive Integer stock,
        @Schema(description = "Product image URL", example = "../potato.png")
        @NotBlank String imageUrl
) { }