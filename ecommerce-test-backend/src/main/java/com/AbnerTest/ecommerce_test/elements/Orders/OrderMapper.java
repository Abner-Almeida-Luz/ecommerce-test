package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.OrderItems;
import com.AbnerTest.ecommerce_test.core.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderDTO(Orders cart);
    OrderItemResponse toOrderItemDTO(OrderItems cartItem);
    OrderItems toOrderItemEntity(OrderItemResponse cart);
}
