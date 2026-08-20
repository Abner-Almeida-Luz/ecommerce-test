package com.AbnerTest.ecommerce_test.elements.Orders;

import com.AbnerTest.ecommerce_test.core.*;
import com.AbnerTest.ecommerce_test.elements.Carts.CartItemRequest;
import com.AbnerTest.ecommerce_test.elements.Carts.CartItemsRepository;
import com.AbnerTest.ecommerce_test.elements.Carts.CartsRepository;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
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
import java.util.ArrayList;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;
import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.ORDERS_LIST_ALL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductsRepository productsRepository;
    @Autowired private CategoriesRepository categoriesRepository;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private CartsRepository cartsRepository;
    @Autowired private CartItemsRepository cartItemsRepository;

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldCheckout() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItems cartItem = cartItemsRepository.save(new CartItems(cart,product,1,product.getPrice().multiply(BigDecimal.valueOf(1))));
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        cart.getCartItems().add(cartItem);

        mockMvc.perform(post(ORDERS + ORDERS_CHECKOUT))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCheckout_blockWhenInvalidSecurity() throws Exception {
        mockMvc.perform(post(ORDERS + ORDERS_CHECKOUT))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldCheckout_thrownException_whenEmptyCart() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));

        mockMvc.perform(post(ORDERS + ORDERS_CHECKOUT))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldCheckout_thrownException_whenInvalidItemPrice() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItems cartItem = cartItemsRepository.save(new CartItems(cart,product,1,BigDecimal.valueOf(99)));
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        cart.getCartItems().add(cartItem);

        mockMvc.perform(post(ORDERS + ORDERS_CHECKOUT))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldListAll() throws Exception {
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", "senha123", UserRole.ADMIN));
        Carts cart = cartsRepository.save(new Carts(user));
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        Products product = productsRepository.save(new Products(category, "Batata","Batata francesa",new BigDecimal(1),1,"batata.png"));
        CartItems cartItem = cartItemsRepository.save(new CartItems(cart,product,1,product.getPrice().multiply(BigDecimal.valueOf(1))));
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        cart.getCartItems().add(cartItem);

        mockMvc.perform(get(ORDERS + ORDERS_LIST_ALL))
                .andExpect(status().isOk());
    }

    @Test
    void shouldListAll_blockWhenInvalidSecurity() throws Exception {
        mockMvc.perform(get(ORDERS + ORDERS_LIST_ALL))
                .andExpect(status().isForbidden());
    }
}