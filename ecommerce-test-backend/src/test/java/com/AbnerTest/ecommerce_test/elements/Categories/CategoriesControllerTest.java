package com.AbnerTest.ecommerce_test.elements.Categories;

import org.junit.jupiter.api.Test;

import com.AbnerTest.ecommerce_test.core.Categories;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.*;
import static com.AbnerTest.ecommerce_test.elements.ApiRoutes.CATEGORIES_CREATE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoriesControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired MockMvc mockMvc;
    @Autowired CategoriesRepository categoriesRepository;

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldListAll() throws Exception {
        Categories category1 = categoriesRepository.save(new Categories("Vegetable1","Fresh Vegetable1"));
        Categories category2 = categoriesRepository.save(new Categories("Vegetable2","Fresh Vegetable2"));

        mockMvc.perform(get(CATEGORIES + CATEGORIES_LIST_ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Vegetable1"))
                .andExpect(jsonPath("$[0].description").value("Fresh Vegetable1"))
                .andExpect(jsonPath("$[1].name").value("Vegetable2"))
                .andExpect(jsonPath("$[1].description").value("Fresh Vegetable2"));
    }

    @Test
    void shouldListAll_blockWhenInvalidSecurity() throws Exception {
        Categories category1 = categoriesRepository.save(new Categories("Vegetable1","Fresh Vegetable1"));
        Categories category2 = categoriesRepository.save(new Categories("Vegetable2","Fresh Vegetable2"));

        mockMvc.perform(get(CATEGORIES + CATEGORIES_LIST_ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldCreate() throws Exception{
        CategoryRequest categoryRequest = new CategoryRequest("Vegetable","Fresh Vegetable");

        mockMvc.perform(post(CATEGORIES + CATEGORIES_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Vegetable"));
    }

    @Test
    void shouldCreate_blockWhenInvalidSecurity() throws Exception{
        CategoryRequest categoryRequest = new CategoryRequest("Vegetable","Fresh Vegetable");

        mockMvc.perform(post(CATEGORIES + CATEGORIES_CREATE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldFindById() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));

        mockMvc.perform(get(CATEGORIES + CATEGORIES_FIND_BY_ID,category.getCategoryId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vegetable"));
    }

    @Test
    void shouldFindById_blockWhenInvalidSecurity() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));

        mockMvc.perform(get(CATEGORIES + CATEGORIES_FIND_BY_ID,category.getCategoryId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldFindById_thrownException_whenCategoryDoesntExists() throws Exception{
        mockMvc.perform(get(CATEGORIES + CATEGORIES_FIND_BY_ID,99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldPutCategoryById() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        CategoryRequest categoryRequest = new CategoryRequest("Vegetable2","Fresh Vegetable2");

        mockMvc.perform(put(CATEGORIES + CATEGORIES_PUT_BY_ID,category.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vegetable2"));
    }

    @Test
    void shouldPutCategoryById_blockWhenInvalidSecurity() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));
        CategoryRequest categoryRequest = new CategoryRequest("Vegetable2","Fresh Vegetable2");

        mockMvc.perform(put(CATEGORIES + CATEGORIES_PUT_BY_ID,category.getCategoryId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldPutCategoryById_thrownException_whenCategoryDoesntExists() throws Exception{
        CategoryRequest categoryRequest = new CategoryRequest("Vegetable2","Fresh Vegetable2");

        mockMvc.perform(put(CATEGORIES + CATEGORIES_PUT_BY_ID,99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldDeleteById() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));

        mockMvc.perform(delete(CATEGORIES + CATEGORIES_DELETE_BY_ID,category.getCategoryId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteById_blockWhenInvalidSecurity() throws Exception{
        Categories category = categoriesRepository.save(new Categories("Vegetable","Fresh Vegetable"));

        mockMvc.perform(delete(CATEGORIES + CATEGORIES_DELETE_BY_ID,category.getCategoryId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "joao@gmail.com", roles = {"USER", "ADMIN"})
    void shouldDeleteById_thrownException_whenCategoryDoesntExists() throws Exception{
        mockMvc.perform(delete(CATEGORIES + CATEGORIES_DELETE_BY_ID,99L))
                .andExpect(status().isNotFound());
    }
}