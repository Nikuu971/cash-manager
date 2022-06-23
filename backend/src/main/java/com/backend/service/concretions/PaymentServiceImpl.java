package com.backend.service.concretions;

import com.backend.exception.InvalidAmount;
import com.backend.exception.InvalidCardException;

import com.backend.model.Card;
import com.backend.model.Payment;
import com.backend.model.UserApplication;
import com.backend.repository.PaymentRepository;
import com.backend.repository.UserRepository;
import com.backend.service.abstractions.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Class Payment Service
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;


    /**
     * Payment Service constructor
     * Init payment service repository and user repository
     *
     * @param paymentRepository Payment Service Repository
     * @param userRepository User Repository
     */
    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find payment by payment Id
     *
     * @param id Payment Id
     * @return payment
     */
    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findById(id).get();
    }

    /**
     * Find all payments
     *
     * @return all find payments
     */
    @Override
    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    /**
     * Process payment
     *
     * @param request Contain all payment informations
     * @return return process payment
     * @throws InvalidCardException if card number, date or CVC is invalid
     */
    @Override
    public Payment savePayment(Payment request, String username) throws InvalidCardException {
        Payment payment = new Payment(request.getMode(), request.getAmount(), request.getCard());
        UserApplication user = userRepository.findByUsername(username);

        if (request.getMode().equals("card")){
            try {
                cardIsValid(request.getCard());
                user.ResetShoppingBasket();
                userRepository.save(user);
            }
            catch (Exception err) {
                throw new InvalidCardException("Invalid Card");
            }
        }
        if (request.getMode().equals("account")){
            if (user.getWallet() >= request.getAmount()) {
                user.setWallet(-request.getAmount());
                user.ResetShoppingBasket();
                userRepository.save(user);
            } else {
                throw new InvalidAmount("No money");
            }
        }

        paymentRepository.save(payment);

        return payment;
    }

    /**
     * Delete payment by payment Id
     *
     * @param id payment Id
     */
    @Override
    public  void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    /**
     * Check card Number with Luhn algorithm
     *
     * @param cardNumber Card Number
     * @return true if card number is valid else return false
     */
    private boolean checkCardNumber(String cardNumber) {
        int[] ints = new int[cardNumber.length()];
        int sum = 0;

        for (int i = 0; i < cardNumber.length(); i++) {
            ints[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }

        return (sum % 10 == 0);
    }

    /**
     * Check if card is valid
     *
     * @param card Card informations
     * @throws InvalidCardException if card number, date or CVC is invlide
     */
    private void cardIsValid(Card card) throws InvalidCardException {
        if (card.getDate().before(new Date())) {
            throw new InvalidCardException("Incorrect Card Date");
        }
        if (card.getCVC().length() != 3) {
            throw new InvalidCardException("Incorrect Card CVC");
        }
        if (!checkCardNumber(card.getCardNumber())) {
            throw new InvalidCardException("Incorrect Card Number");
        }
    }
}
