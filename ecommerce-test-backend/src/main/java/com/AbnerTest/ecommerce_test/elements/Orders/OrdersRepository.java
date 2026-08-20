package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o JOIN FETCH o.orderItems oi JOIN FETCH oi.product " +
            "WHERE o.user.login = :login")
    List<Orders> findByUserLoginWithItems(@Param("login") String login);
}
