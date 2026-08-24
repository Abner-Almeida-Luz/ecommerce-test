package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.OrderItems;
import com.AbnerTest.ecommerce_test.core.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "orderItems", target = "items")
    OrderResponse toOrderDTO(Orders order);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "total", target = "total")
    OrderItemResponse toOrderItemDTO(OrderItems item);

    OrderItems toOrderItemEntity(OrderItemResponse cart);
}