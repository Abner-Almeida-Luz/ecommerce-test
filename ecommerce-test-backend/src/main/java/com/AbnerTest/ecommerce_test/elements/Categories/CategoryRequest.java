package com.AbnerTest.ecommerce_test.elements.Categories;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new category")
public record CategoryRequest(
        @Schema(description = "Category name", example = "Vegetable")
        @NotBlank String name,
        @Schema(description = "Category description", example = "Fresh Vegetable")
        @NotBlank String description
) { }