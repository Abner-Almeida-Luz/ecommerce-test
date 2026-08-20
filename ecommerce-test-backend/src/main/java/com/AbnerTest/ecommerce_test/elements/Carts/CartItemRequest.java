package com.AbnerTest.ecommerce_test.elements.Carts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request to create a new cart item")
public record CartItemRequest(
        @Schema(description = "Category id", example = "1")
        @NotNull @Positive Long cartId,
        @Schema(description = "Product id", example = "1")
        @NotNull @Positive Long productId,
        @Schema(description = "Category id", example = "1")
        @Positive Integer quantity
) { }