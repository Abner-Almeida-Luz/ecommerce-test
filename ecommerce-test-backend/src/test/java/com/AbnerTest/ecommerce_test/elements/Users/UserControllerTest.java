package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.*;
import com.AbnerTest.ecommerce_test.elements.Carts.CartItemsRepository;
import com.AbnerTest.ecommerce_test.elements.Carts.CartsRepository;
import com.AbnerTest.ecommerce_test.elements.Categories.CategoriesRepository;
import com.AbnerTest.ecommerce_test.elements.Orders.OrdersRepository;
import com.AbnerTest.ecommerce_test.elements.Products.ProductsRepository;
import com.AbnerTest.ecommerce_test.infra.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductsRepository productsRepository;
    @Autowired private CategoriesRepository categoriesRepository;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private CartsRepository cartsRepository;
    @Autowired private CartItemsRepository cartItemsRepository;
    @Autowired private TokenService tokenService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldListAllUsers() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        Users user1 = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN));
        Users user2 = userRepository.save(new Users("maria@gmail.com", "maria@gmail.com", encryptedPassword, UserRole.ADMIN));

        mockMvc.perform(get(USERS + USERS_LIST_ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()));
    }

    @Test
    void shouldListAllUsers_blockWhenInvalidSecurity() throws Exception {
        mockMvc.perform(get(USERS + USERS_LIST_ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldLogin() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN));
        LoginRequest loginRequest = new LoginRequest("joao@gmail.com", "senha123");

        mockMvc.perform(post(USERS + USERS_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.acessToken").exists())
                .andExpect(jsonPath("$.acessToken").exists());
    }


    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldLogin_thrownException_whenUserDoesntExists() throws Exception {
        LoginRequest loginRequest = new LoginRequest("ghost@gmail.com", "wrong password");

        mockMvc.perform(post(USERS + USERS_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRegister() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        RegisterRequest registerRequest = new RegisterRequest("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN);

        mockMvc.perform(post(USERS + USERS_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login").value("joao@gmail.com"));
        //verify Cart Save
    }

    @Test
    void shouldRegister_thrownException_whenDuplicatedLogin() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN));
        RegisterRequest registerRequest = new RegisterRequest("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN);

        mockMvc.perform(post(USERS + USERS_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldFindByLogin() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN));

        mockMvc.perform(get(USERS + USERS_FIND_BY_LOGIN, user.getLogin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(user.getLogin()));
    }

    @Test
    void shouldFindByLogin_blockWhenInvalidSecurity() throws Exception {
        mockMvc.perform(get(USERS + USERS_FIND_BY_LOGIN, 99L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldFindByLogin_thrownException_whenUserDoesntExists() throws Exception {
        mockMvc.perform(get(USERS + USERS_FIND_BY_LOGIN, "ghost@gmail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldDeleteByLogin() throws Exception {
        String encryptedPassword = passwordEncoder.encode("senha123");
        Users user = userRepository.save(new Users("joao@gmail.com", "joao@gmail.com", encryptedPassword, UserRole.ADMIN));

        mockMvc.perform(delete(USERS + USERS_DELETE_BY_ID, user.getUserId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteByLogin_blockWhenInvalidSecurity() throws Exception {
        mockMvc.perform(delete(USERS + USERS_DELETE_BY_ID, 99L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldDeleteByLogin_thrownException_whenUserDoesntExists() throws Exception {
        mockMvc.perform(delete(USERS + USERS_DELETE_BY_ID, 99L))
                .andExpect(status().isNotFound());
    }
}