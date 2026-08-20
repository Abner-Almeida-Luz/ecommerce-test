package com.AbnerTest.ecommerce_test.elements.Categories;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> findAll(){
        return categoriesRepository.findAll().stream().map(categoryMapper::toDTO).toList();
    }

    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse findById(Long id){
        return categoryMapper.toDTO(categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id)));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse create(CategoryRequest request) {
        return categoryMapper.toDTO(categoriesRepository.save(new Categories(request.name(),request.description())));
    }

    @CacheEvict(value = "categories", key = "#id")
    public CategoryResponse put(Long id, CategoryRequest categoriesRequest) {
        log.info("Putting category with id={} ", id);
        Categories category = categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(categoriesRequest.name());
        category.setDescription(categoriesRequest.description());
        log.info("Putting category completed. categoryId={} categoryRequest={} ", id, categoriesRequest);
        return categoryMapper.toDTO(categoriesRepository.save(category));
    }

    @CacheEvict(value = "categories", key = "#id")
    public void delete(Long id) {
        if (!categoriesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoriesRepository.deleteById(id);
    }
}