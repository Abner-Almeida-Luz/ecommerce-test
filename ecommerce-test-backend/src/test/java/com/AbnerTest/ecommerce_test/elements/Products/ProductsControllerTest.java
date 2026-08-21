package com.AbnerTest.ecommerce_test.elements.Products;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.AbnerTest.ecommerce_test.core.Products;
import com.AbnerTest.ecommerce_test.elements.Carts.CartItemsRepository;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.AbnerTest.ecommerce_test.elements.Orders.OrdersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductsControllerTest {
    @Autowired MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired ProductsRepository productsRepository;
    @Autowired CategoriesRepository categoriesRepository;
    @Autowired CartItemsRepository cartItemsRepository;
    @Autowired OrdersRepository ordersRepository;

    @BeforeEach
    void cleanDatabase() {
        cartItemsRepository.deleteAll();
        ordersRepository.deleteAll();
        productsRepository.deleteAll();
        categoriesRepository.deleteAll();
    }

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
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 1')]").exists())
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 2')]").exists())
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 3')]").exists());
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
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 1')]").exists())
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 2')]").exists())
                .andExpect(jsonPath("$.content[?(@.name == 'Batata 3')]").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    void shouldFindProductById() throws Exception {
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));

        mockMvc.perform(get(PRODUCTS + PRODUCTS_FIND_BY_ID,product.getProductId()))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Batata"));;
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
                .andExpect(jsonPath("$.name").value("Batata"));;
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