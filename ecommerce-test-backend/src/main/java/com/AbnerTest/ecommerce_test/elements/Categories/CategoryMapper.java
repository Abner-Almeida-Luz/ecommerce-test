package com.AbnerTest.ecommerce_test.elements.Categories;

import com.AbnerTest.ecommerce_test.core.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toDTO(Categories category);
    Categories toEntity(CategoryRequest dto);
}
