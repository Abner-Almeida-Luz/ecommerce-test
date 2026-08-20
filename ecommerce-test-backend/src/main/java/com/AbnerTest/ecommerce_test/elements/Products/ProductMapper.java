package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.core.Products;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductSummaryResponse toSummaryDTO(Products products);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toDTO(Products product);
    Products toEntity(ProductRequest dto);

}
