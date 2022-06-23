package com.backend.controller;

import com.backend.exception.InvalidAmount;
import com.backend.exception.InvalidCardException;
import com.backend.model.Payment;
import com.backend.service.abstractions.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class Rest API Payment Controller
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RestController
@RequestMapping(PaymentController.BASE_URL)
public class PaymentController {

    public  static final String BASE_URL = "/api/v1/payments";

    private final IPaymentService paymentService;

    /**
     * Payment Controller constructor
     * Init payment controller service
     * @param paymentService   Payment Service
     */
    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Get all payments
     *
     * @return Payment list
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Payment> getAllPayments() {
        return paymentService.findAllPayment();
    }

    /**
     * Get payments by payment Id
     *
     * @param id Payment Id
     * @return Payment
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  Payment getPaymentById(@PathVariable long id) {
        return paymentService.findPaymentById(id);
    }

    /**
     * Process payment
     *
     * @param payment Request body how contain payment informations
     * @return Process payment if success or Bad Request if fail
     */
    @PostMapping
    public ResponseEntity<?> savePayment(@RequestBody Payment payment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            Payment payment1 = paymentService.savePayment(payment, authentication.getName());
            return new ResponseEntity<>(payment1, HttpStatus.CREATED);
        }
        catch (InvalidCardException exc) {
            return new ResponseEntity<>("Invalid Card", HttpStatus.BAD_REQUEST);
        }
        catch (InvalidAmount exc) {
            return new ResponseEntity<>("not enough money", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete Payment in database
     *
     * @param id Payment Id
     */
    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable long id) {
        paymentService.deletePayment(id);
    }
}
