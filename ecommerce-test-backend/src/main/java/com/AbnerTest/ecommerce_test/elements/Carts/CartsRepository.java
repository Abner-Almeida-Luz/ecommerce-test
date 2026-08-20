package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {
    @Query("""
SELECT c
FROM Carts c
LEFT JOIN FETCH c.cartItems
WHERE c.user.id = :id
""")
    Optional<Carts> findCartByUserId(Long id);
}
