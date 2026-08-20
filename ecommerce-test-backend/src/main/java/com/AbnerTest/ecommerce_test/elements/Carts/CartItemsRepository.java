package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}
