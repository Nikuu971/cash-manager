package com.backend.service.abstractions;

import com.backend.model.Payment;

import java.util.List;

public interface IPaymentService {
    Payment findPaymentById(Long id);

    List<Payment> findAllPayment();

    Payment savePayment(Payment payment, String username);

    void deletePayment(Long id);
}
