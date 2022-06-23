package com.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.backend.exception.InvalidAmount;
import com.backend.exception.InvalidCardException;
import com.backend.model.Card;
import com.backend.model.Payment;
import com.backend.model.UserApplication;
import com.backend.repository.UserRepository;
import com.backend.service.abstractions.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class Payment Controller Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PaymentController.class, secure = false)
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IPaymentService service;


    private Card card;

    /**
     * Init valid Card test
     *
     * @return new Card
     */
    private Card InitCard() {
        card = new Card();
        card.setCardNumber("4532621865518890");
        card.setCVC("432");
        card.setFirstname("Jean");
        card.setLastname("Paul");
        card.setDate(new Date(2080, 7, 15));
        return card;
    }

    /**
     * Test process payment route
     *
     * @throws Exception if payment route doesn't return created
     */
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void createPaymentsTest() throws Exception {
        UserApplication user = new UserApplication("a");
        Payment p1 = new Payment("card", 48.85, InitCard());
        String json = mapper.writeValueAsString(p1);

        this.mockMvc.perform(post("/api/v1/payments")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    /**
     * Test process payment route error
     *
     * @throws Exception if payment route doesn't return Bad Request
     */
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void createPaymentsErrorTest() throws Exception {
        UserApplication user = new UserApplication("a");
        Payment p1 = new Payment("card", 48.85, InitCard());
        String json = mapper.writeValueAsString(p1);

        given(service.savePayment(p1, "user1")).willThrow(new InvalidCardException("Invalid Card"));
        this.mockMvc.perform(post("/api/v1/payments")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test process payment route error
     *
     * @throws Exception if payment route doesn't return Bad Request
     */
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void createPaymentsErrorMoneyTest() throws Exception {
        UserApplication user = new UserApplication("user1");
        Payment p1 = new Payment("account", 102, InitCard());
        String json = mapper.writeValueAsString(p1);

        given(service.savePayment(p1, "user1")).willThrow(new InvalidAmount("no money"));
        this.mockMvc.perform(post("/api/v1/payments")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test payment get by id route
     *
     * @throws Exception if payment get isn't correct
     */
    @Test
    public void getPaymentsByIdTest() throws Exception {
        final Long id = 1L;
        Payment p1 = new Payment("card", 48.85, InitCard());
        p1.setId(id);

        given(service.findPaymentById(id)).willReturn(p1);

        this.mockMvc.perform(get("/api/v1/payments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode", is("card")))
                .andExpect(jsonPath("$.amount", is(48.85)));
    }

    /**
     * Test get all payments route
     *
     * @throws Exception if payments list isn't correct
     */
    @Test
    public void getAllPaymentsTest() throws Exception {
        Payment p1 = new Payment("card", 48.85, InitCard());

        List<Payment> allPayments = Arrays.asList(p1);

        given(service.findAllPayment()).willReturn(allPayments);

        this.mockMvc.perform(get("/api/v1/payments")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].mode", is("card")))
                .andExpect(jsonPath("$[0].amount", is(48.85)));
    }

    /**
     * Test delete payment by id route
     *
     * @throws Exception if route doesn't return Ok status
     */
    @Test
    public void deletePaymentsByIdTest() throws Exception {
        final Long id = 1L;
        Payment p1 = new Payment("card", 48.85, InitCard());
        p1.setId(id);

        given(service.findPaymentById(id)).willReturn(p1);

        this.mockMvc.perform(delete("/api/v1/payments/{id}", id))
                .andExpect(status().isOk());
    }
}