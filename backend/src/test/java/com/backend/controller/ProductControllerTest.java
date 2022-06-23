package com.backend.controller;

import com.backend.model.Product;
import com.backend.service.abstractions.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Product Controller Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class, secure = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IProductService service;

    /**
     * Test create product route
     *
     * @throws Exception if route doesn't return created status
     */
    @Test
    public void createProductTest() throws Exception {
        Product p1 = new Product(4.5, "product_1");
        String json = mapper.writeValueAsString(p1);

        this.mockMvc.perform(post("/api/v1/products")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    /**
     * Test delete product route
     *
     * @throws Exception if route doesn't return ok status
     */
    @Test
    public void deleteProductByIdTest() throws Exception {
        final Long id = 1L;
        Product p1 = new Product(4.5, "product_1");
        p1.setId(id);

        given(service.getProductById(id)).willReturn(p1);

        this.mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isOk());
    }
}