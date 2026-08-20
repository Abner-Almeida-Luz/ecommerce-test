package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.core.Products;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductsControllerTest {
    @Autowired MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired ProductsRepository productsRepository;
    @Autowired CategoriesRepository categoriesRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldListAllProducts() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product1 = productsRepository.save(new Products(category, "Batata 1","Batata francesa",new BigDecimal(1),1,"batata.png"));
        Products product2 = productsRepository.save(new Products(category, "Batata 2","Batata francesa",new BigDecimal(1),1,"batata.png"));
        Products product3 = productsRepository.save(new Products(category, "Batata 3","Batata francesa",new BigDecimal(1),1,"batata.png"));

        mockMvc.perform(get(PRODUCTS + PRODUCTS_LIST_ALL)
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value(product1.getName()))
                .andExpect(jsonPath("$.content[1].name").value(product2.getName()))
                .andExpect(jsonPath("$.content[2].name").value(product3.getName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldListAllSearchedProducts() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product1 = productsRepository.save(new Products(category, "Batata 1","Batata francesa",new BigDecimal(1),1,"batata.png"));
        Products product2 = productsRepository.save(new Products(category, "Batata 2","Batata francesa",new BigDecimal(1),1,"batata.png"));
        Products product3 = productsRepository.save(new Products(category, "Batata 3","Batata francesa",new BigDecimal(1),1,"batata.png"));

        SearchProductRequest request = new SearchProductRequest("Batata",category.getCategoryId(),new BigDecimal(1),new BigDecimal(100),0,3);

        mockMvc.perform(post(PRODUCTS + PRODUCTS_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value(product1.getName()))
                .andExpect(jsonPath("$.content[1].name").value(product2.getName()))
                .andExpect(jsonPath("$.content[2].name").value(product3.getName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldFindProductById() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));

        mockMvc.perform(get(PRODUCTS + PRODUCTS_FIND_BY_ID,product.getProductId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(product.getProductId()))
        .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldThrownExceptionWhenFindProductDoesntExists() throws Exception {
        mockMvc.perform(get(PRODUCTS + PRODUCTS_FIND_BY_ID,99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldCreateProduct() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        ProductRequest request = new ProductRequest(category.getCategoryId(), "Batata","Batata francesa",new BigDecimal(1),1,"batata.png");

        mockMvc.perform(post(PRODUCTS + PRODUCTS_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Batata"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldThrownExceptionWhenCreateCategoryDoesntExists() throws Exception {
        ProductRequest request = new ProductRequest(99L, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png");

        mockMvc.perform(post(PRODUCTS + PRODUCTS_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldPutProduct() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        ProductRequest request = new ProductRequest(category.getCategoryId(), "Updated","Batata francesa",new BigDecimal(1),1,"batata.png");

    mockMvc.perform(put(PRODUCTS + PRODUCTS_PUT_BY_ID,product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product.getProductId()))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldThrownExceptionWhenPutProductDoesntExists() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        ProductRequest request = new ProductRequest(99L, "Updated","Batata francesa",new BigDecimal(1),1,"batata.png");

        mockMvc.perform(put(PRODUCTS + PRODUCTS_PUT_BY_ID,99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldThrownExceptionWhenPutCategoryDoesntExists() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        ProductRequest request = new ProductRequest(99L, "Updated","Batata francesa",new BigDecimal(1),1,"batata.png");

        mockMvc.perform(put(PRODUCTS + PRODUCTS_PUT_BY_ID,product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldDeleteProduct() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));

        mockMvc.perform(delete(PRODUCTS + PRODUCTS_DELETE_BY_ID,product.getProductId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldThrownExceptionWhenDeleteProductDoesntExists() throws Exception {
        mockMvc.perform(delete(PRODUCTS + PRODUCTS_DELETE_BY_ID,99L))
                .andExpect(status().isNotFound());
    }
}