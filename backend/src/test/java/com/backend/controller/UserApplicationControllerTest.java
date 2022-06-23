package com.backend.controller;

import com.backend.exception.UserException;
import com.backend.model.UserApplication;
import com.backend.service.abstractions.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Class User Controller Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
public class UserApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IUserService service;

    /**
     * Test create user route
     *
     * @throws Exception if route doesn't return created status
     */
    @Test
    public void createUserTest() throws Exception {
        UserApplication userApplication = new UserApplication("Jean");
        String json = mapper.writeValueAsString(userApplication);

        this.mockMvc.perform(post("/api/v1/users")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    /**
     * Test get user by id route
     *
     * @throws Exception if route doesn't return correct user
     */
    @Test
    public void getUserByIdTest() throws Exception {
        final Long id = 1L;
        UserApplication userApplication = new UserApplication("Jean");
        userApplication.setId(id);

        given(service.getUserById(id)).willReturn(userApplication);

        this.mockMvc.perform(get("/api/v1/users/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Jean")));
    }

    /**
     * Test get user by id error
     *
     * @throws Exception if route doesn't return Bad Request status
     */
    @Test
    public void getUserByIdErrorTest() throws Exception {
        final Long id = 1L;

        given(service.getUserById(id)).willThrow(new UserException("Invalid UserId"));

        this.mockMvc.perform(get("/api/v1/users/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}