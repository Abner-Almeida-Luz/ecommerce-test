package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.core.Products;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
    @Mock ProductsRepository productsRepository;
    @Mock CategoriesRepository categoriesRepository;
    @Mock ProductMapper productMapper;

    @InjectMocks ProductsService productsService;

    @Test
    @DisplayName("Find product when exists return Product Response")
    void findById_whenExists_returnProductResponse() {
        Categories category = new Categories("Vegetable","Fresh Vegetable");
        Products product = new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png");
        ProductResponse expected = new ProductResponse(product.getProductId(),product.getCategory().getCategoryId(),product.getCategory().getName(),product.getName(),product.getDescription(),product.getPrice(),product.getStock(),product.getImageUrl(),product.getCreatedAt());
        Long request = product.getProductId();

        when(productsRepository.findById(request)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(expected);

        ProductResponse result = productsService.findById(request);

        assertThat(result).isEqualTo(expected);
        verify(productsRepository).findById(request);
    }

    @Test
    @DisplayName("Return exception if product doesnt exists")
    void findById_whenDoesntExists_throwException() {
        when(productsRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productsService.findById(99L)).isInstanceOf(Exceptions.ResourceNotFoundException.class).hasMessageContaining("Product not found");
    }

    @Test
    @DisplayName("Put product when exists return Product Response")
    void put() {
        Categories lastCategory = new Categories("Vegetable","Fresh Vegetable");
        Categories newCategory = new Categories("Spring Vegetable","Fresh Spring Vegetable");
        Products product = new Products(lastCategory, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png");
        ProductResponse expected = new ProductResponse(product.getProductId(),newCategory.getCategoryId(),product.getCategory().getName(),product.getName(),product.getDescription(),product.getPrice(),product.getStock(),product.getImageUrl(),product.getCreatedAt());
        ProductRequest request = new ProductRequest(newCategory.getCategoryId(), "Cenora","Cenora francesa",new BigDecimal(1),1,"cenora.png");

        when(productsRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(categoriesRepository.findById(newCategory.getCategoryId())).thenReturn(Optional.of(newCategory));
        when(productsRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(expected);

        ProductResponse result = productsService.put(product.getProductId(), request);

        assertThat(result).isEqualTo(expected);
        verify(productsRepository).save(product);
    }
}