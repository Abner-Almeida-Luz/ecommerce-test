package com.AbnerTest.ecommerce_test.elements.Carts;

import com.AbnerTest.ecommerce_test.core.*;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
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
import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.CARTS_ADD_ITEM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CartControllerTest {
    @Autowired MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired CartsService cartsService;
    @Autowired UserRepository userRepository;
    @Autowired CartsRepository cartsRepository;
    @Autowired CartItemsRepository cartItemsRepository;
    @Autowired CategoriesRepository categoriesRepository;
    @Autowired ProductsRepository productsRepository;

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldViewCart() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));

        mockMvc.perform(get(CARTS + CARTS_VIEW_CART))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(cart.getCartId()));
    }

    @Test
    void shouldViewCart_blockWhenInvalidSecurity() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));

        mockMvc.perform(get(CARTS + CARTS_VIEW_CART))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldAddItemToCart() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItemRequest cartItemRequest = new CartItemRequest(cart.getCartId(),product.getProductId(),1);

        mockMvc.perform(post(CARTS + CARTS_ADD_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartId").value(cart.getCartId()));
    }

    @Test
    void shouldAddItemToCart_blockWhenInvalidSecurity() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItemRequest cartItemRequest = new CartItemRequest(cart.getCartId(),product.getProductId(),1);

        mockMvc.perform(post(CARTS + CARTS_ADD_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldAddItemToCart_thrownException_WhenProductDoesntExists() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        CartItemRequest cartItemRequest = new CartItemRequest(cart.getCartId(),99L,1);

        mockMvc.perform(post(CARTS + CARTS_ADD_ITEM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldDeleteItemFromCart() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItems cartItem = cartItemsRepository.save(new CartItems(cart,product,1,product.getPrice().multiply(BigDecimal.valueOf(1))));

        mockMvc.perform(delete(CARTS + CARTS_DELETE_ITEM,cartItem.getCartItemId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteItemFromCart_blockWhenInvalidSecurity() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItems cartItem = cartItemsRepository.save(new CartItems(cart,product,1,product.getPrice().multiply(BigDecimal.valueOf(1))));

        mockMvc.perform(delete(CARTS + CARTS_DELETE_ITEM,cartItem.getCartItemId()))
                .andExpect(status().isForbidden());
    }
}