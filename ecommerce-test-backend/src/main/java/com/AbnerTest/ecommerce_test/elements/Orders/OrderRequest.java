package com.AbnerTest.ecommerce_test.elements.Orders;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request to create a new order")
public record OrderRequest(
        @Schema(description = "User id", example = "1")
        @NotNull @Positive Long userId
) { }