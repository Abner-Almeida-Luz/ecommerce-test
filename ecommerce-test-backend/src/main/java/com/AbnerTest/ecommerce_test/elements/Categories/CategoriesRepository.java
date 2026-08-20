package com.AbnerTest.ecommerce_test.elements.Categories;

import com.AbnerTest.ecommerce_test.core.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
