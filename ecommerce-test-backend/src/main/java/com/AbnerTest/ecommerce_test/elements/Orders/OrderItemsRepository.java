package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}
