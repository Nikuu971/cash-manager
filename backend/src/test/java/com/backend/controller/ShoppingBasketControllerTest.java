package com.backend.controller;

import com.backend.exception.FindProductException;
import com.backend.exception.UserException;
import com.backend.model.Product;
import com.backend.model.ShoppingBasket;
import com.backend.service.abstractions.IShoppingBasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Shopping Basket Controller Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ShoppingBasketController.class, secure = false)
public class ShoppingBasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IShoppingBasketService service;

    /**
     * Test get shopping basket route
     *
     * @throws Exception if route doesn't return ok status
     */
    @Test
    public void getShoppingBasketTest() throws Exception {
        final Long id = 1L;
        ShoppingBasket p1 = new ShoppingBasket();

        given(service.getShoppingBasket(id)).willReturn(p1);

        this.mockMvc.perform(get("/api/v1/users/{userId}/shoppingbaskets", id))
                .andExpect(status().isOk());
    }

    /**
     * Test get shopping basket route error
     *
     * @throws Exception if route doesn't return Bad Request
     */
    @Test
    public void getShoppingBasketErrorTest() throws Exception {
        final Long id = 1L;

        given(service.getShoppingBasket(id)).willThrow(new UserException("Invalid User Id"));

        this.mockMvc.perform(get("/api/v1/users/{userId}/shoppingbaskets", id))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test add article route
     *
     * @throws Exception if route doesn't return valid shopping basket
     */
    @Test
    public void addArticleTest() throws Exception {
        final Long id = 1L;
        Product article = new Product(4.8, "product");
        ShoppingBasket p1 = new ShoppingBasket();
        p1.AddArticle(article);
        p1.setId(id);
        String json = mapper.writeValueAsString(p1);

        given(service.addArticle(id, id)).willReturn(p1);

        MvcResult res = this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/add/{id}", id, id))
                .andExpect(status().isCreated())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        assertEquals(content, json);
    }

    /**
     * Test add article route product error
     *
     * @throws Exception if route doesn't return Bad Request status
     */
    @Test
    public void addArticleProductErrorTest() throws Exception {
        final Long id = 1L;

        given(service.addArticle(id, id)).willThrow(new FindProductException("Invalid Product Id"));

        this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/add/{id}", id, id))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test add article route user error
     *
     * @throws Exception if route doesn't return Bad Request status
     */
    @Test
    public void addArticleUserErrorTest() throws Exception {
        final Long id = 1L;

        given(service.addArticle(id, id)).willThrow(new UserException("Invalid User Id"));

        this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/add/{id}", id, id))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test remove article route
     *
     * @throws Exception if route doesn't return valid shopping basket
     */
    @Test
    public void deleteArticleTest() throws Exception {
        final Long id = 1L;
        Product article = new Product(4.8, "product");
        ShoppingBasket p1 = new ShoppingBasket();
        p1.AddArticle(article);
        p1.AddArticle(article);
        p1.setId(id);
        String json = mapper.writeValueAsString(p1);

        given(service.removeArticle(id, id)).willReturn(p1);

        MvcResult res = this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/delete/{id}", id, id))
                .andExpect(status().isOk())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        assertEquals(content, json);
    }

    /**
     * Test remove article route product error
     *
     * @throws Exception if route doesn't return Bad Request status
     */
    @Test
    public void deleteArticleProductErrorTest() throws Exception {
        final Long id = 1L;


        given(service.removeArticle(id, id)).willThrow(new FindProductException("Invalid Product Id"));

        this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/delete/{id}", id, id))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test remove article route user error
     *
     * @throws Exception if route doesn't return Bad Request status
     */
    @Test
    public void deleteArticleUserErrorTest() throws Exception {
        final Long id = 1L;


        given(service.removeArticle(id, id)).willThrow(new UserException("Invalid User Id"));

        this.mockMvc.perform(post("/api/v1/users/{userId}/shoppingbaskets/delete/{id}", id, id))
                .andExpect(status().isBadRequest());
    }
}