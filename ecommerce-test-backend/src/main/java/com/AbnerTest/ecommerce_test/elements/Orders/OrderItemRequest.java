package com.AbnerTest.ecommerce_test.elements.Orders;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request o create a new order item")
public record OrderItemRequest(
        @Schema(description = "Order id", example = "1")
        @NotNull @Positive Long orderId,
        @Schema(description = "Product id", example = "1")
        @NotNull @Positive Long productId,
        @Schema(description = "Quantity", example = "1")
        @Positive Integer quantity
) { }