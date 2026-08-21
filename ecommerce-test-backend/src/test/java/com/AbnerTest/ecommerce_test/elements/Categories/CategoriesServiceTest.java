package com.AbnerTest.ecommerce_test.elements.Categories;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {
    @Mock CategoriesRepository categoriesRepository;
    @Mock CategoryMapper categoryMapper;

    @InjectMocks CategoriesService categoriesService;

    @Test
    @DisplayName("Find category when exists return Category Response")
    void findById_whenExists_returnCategoryResponse() {
        Categories category = new Categories("Vegetable","Fresh Vegetable");
        CategoryResponse expected = new CategoryResponse(category.getCategoryId(),category.getName(),category.getDescription());

        when(categoriesRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        when(categoryMapper.toDTO(category)).thenReturn(expected);

        CategoryResponse result = categoriesService.findById(category.getCategoryId());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return exception if category find doesnt exists")
    void findById_whenDoesntExists_throwException() {
        when(categoriesRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriesService.findById(99L)).isInstanceOf(Exceptions.ResourceNotFoundException.class).hasMessageContaining("Category not found");
    }

    @Test
    @DisplayName("Put category when exists return Category Response")
    void put_whenExists_returnCategoryResponse() {
        Categories category = new Categories("Vegetable","Fresh Vegetable");
        CategoryResponse expected = new CategoryResponse(category.getCategoryId(),category.getName(),category.getDescription());
        CategoryRequest request = new CategoryRequest("Car","Semi news cars");

        when(categoriesRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        when(categoriesRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(expected);

        CategoryResponse result = categoriesService.put(category.getCategoryId(),request);

        assertThat(result).isEqualTo(expected);
        verify(categoriesRepository).save(category);
    }

    @Test
    @DisplayName("Return exception if category put doesnt exists")
    void put_whenDoesntExists_throwException() {
        CategoryRequest request = new CategoryRequest("Vegetable","Fresh Vegetable");

        when(categoriesRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriesService.put(99L,request)).isInstanceOf(Exceptions.ResourceNotFoundException.class).hasMessageContaining("Category not found");
    }
}