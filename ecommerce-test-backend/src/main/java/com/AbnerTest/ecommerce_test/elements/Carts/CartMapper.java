package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.CartItems;
import com.AbnerTest.ecommerce_test.core.Carts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toCartDTO(Carts cart);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.imageUrl", target = "productImageUrl")
    CartItemResponse toCartItemDTO(CartItems cartItem);
    CartItems toCartItemEntity(CartItemResponse cart);
}
