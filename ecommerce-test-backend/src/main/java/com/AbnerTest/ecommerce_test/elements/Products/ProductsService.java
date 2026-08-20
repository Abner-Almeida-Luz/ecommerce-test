package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.core.Products;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions.ResourceNotFoundException;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductMapper productMapper;

    public Page<ProductSummaryResponse> findAll(Integer page, Integer size, String sort) {
        log.info("Find All Products by Page={}",List.of(page, size,sort));
        Pageable pageable = PageRequest.of(page, size,Sort.by(sort));
        return productsRepository.findAll(pageable)
                .map(productMapper::toSummaryDTO);
    }

    public Page<ProductSummaryResponse> search(SearchProductRequest request) {
        log.info("Find All Products by Search={}", request.toString());
        Pageable pageable = PageRequest.of(request.page(), request.size());
        return productsRepository.findByFilters(request.name(),request.categoryId(),request.minPrice(),request.maxPrice(),pageable)
                .map(productMapper::toSummaryDTO);
    }

    public ProductResponse findById(Long id){
        log.info("Finding Product by Id={}",id);
        return productMapper.toDTO(productsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }

    public ProductResponse create(ProductRequest request){
        log.info("Creating Product={}",request);
        Categories category = categoriesRepository.findById(request.categoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "  + request.categoryId()));
        Products product = new Products(category,request.name(), request.description(), request.price(), request.stock(), request.imageUrl());
        log.info("Creating product completed. productRequest={} productId={}",request,product.getProductId());
        return productMapper.toDTO(productsRepository.save(product));
    }

    @Transactional
    public ProductResponse put(Long id, ProductRequest request){
        log.info("Updating Product={}",id);
        Products product = productsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setImageUrl(request.imageUrl());
        product.setCategory(
                categoriesRepository.findById(request.categoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "  + request.categoryId())));
        productsRepository.save(product);
        log.info("Updating completed. productId={} productPutting={}",id, request);
        return productMapper.toDTO(product);
    }

    public void delete(Long id){
        log.info("Deleting Product={}",id);
        if(!productsRepository.existsById(id)){
            throw new ResourceNotFoundException("Product not found with id: "  + id);
        }
        productsRepository.deleteById(id);
    }
}