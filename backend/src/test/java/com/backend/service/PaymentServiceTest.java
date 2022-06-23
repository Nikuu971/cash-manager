package com.backend.service;

import com.backend.exception.InvalidAmount;
import com.backend.exception.InvalidCardException;
import com.backend.model.Card;
import com.backend.model.Payment;
import com.backend.model.UserApplication;
import com.backend.repository.PaymentRepository;
import com.backend.repository.UserRepository;
import com.backend.service.concretions.PaymentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Class Payment Service Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    private Card card;

    /**
     * Init valid Card Test
     *
     * @return new Card
     */
    private Card InitCard() {
        card = new Card();
        card.setCardNumber("4532621865518890");
        card.setCVC("432");
        card.setFirstname("Jean");
        card.setLastname("Paul");
        card.setDate(new GregorianCalendar(2022, Calendar.FEBRUARY, 11).getTime());

        return card;
    }

    /**
     * Test get all payment method
     * fail if payment data isn't correct
     */
    @Test
    public void getAllPayment() {
        Payment p1 = new Payment("card", 48.85, InitCard());

        List<Payment> datas = Arrays.asList(p1);

        given(paymentRepository.findAll()).willReturn(datas);

        List<Payment> expected = paymentService.findAllPayment();

        assertEquals(expected, datas);
    }

    /**
     * Test get payment by id method
     * fail if payment data is null
     */
    @Test
    public void getPaymentById() {
        final Long id = 1L;
        Payment p1 = new Payment("card", 48.85, InitCard());

        p1.setId(id);

        given(paymentRepository.findById(id)).willReturn(Optional.of(p1));

        final Optional<Payment> expected = Optional.ofNullable(paymentService.findPaymentById(id));

        assertThat(expected).isNotNull();
    }

    /**
     * Test create payment method
     * fail if payment data isn't correct
     */
    @Test
    public void createPayment() {
        UserApplication userApplication = new UserApplication("a");
        Payment p1 = new Payment("card", 48.5, InitCard());

        given(userRepository.findByUsername("a")).willReturn(userApplication);
        Payment create = paymentService.savePayment(p1, "a");

        assertEquals(create.getAmount(), p1.getAmount(), 0);
        assertEquals(create.getMode(), p1.getMode());
    }

    /**
     * Test create payment method card number error
     * fail if method doesn't throw
     */
    @Test(expected = InvalidCardException.class)
    public void createInvalidPaymentCardNumber() {
        UserApplication userApplication = new UserApplication("a");
        Card invalidCard = InitCard();
        invalidCard.setCardNumber("4532621865518891");
        Payment p1 = new Payment("card", 48.5, invalidCard);

        given(userRepository.findByUsername("a")).willReturn(userApplication);

        Payment create = paymentService.savePayment(p1, "a");
    }

    /**
     * Test create payment method card date error
     * fail if method doesn't throw
     */
    @Test(expected = InvalidCardException.class)
    public void createInvalidPaymentCardDate() {
        UserApplication userApplication = new UserApplication("a");
        Card invalidCard = InitCard();
        invalidCard.setDate(new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime());
        Payment p1 = new Payment("card", 48.5, invalidCard);

        given(userRepository.findByUsername("a")).willReturn(userApplication);
        Payment create = paymentService.savePayment(p1, "a");
    }

    /**
     * Test create payment method card CVC error
     * fail if method doesn't throw
     */
    @Test(expected = InvalidCardException.class)
    public void createInvalidPaymentCardCVC() {
        UserApplication userApplication = new UserApplication("a");
        Card invalidCard = InitCard();
        invalidCard.setCVC("4532621865518891");
        Payment p1 = new Payment("card", 48.5, invalidCard);

        given(userRepository.findByUsername("a")).willReturn(userApplication);
        Payment create = paymentService.savePayment(p1, "a");
    }

    /**
     * Test create payment method money error
     * fail if method doesn't throw
     */
    @Test(expected = InvalidAmount.class)
    public void createInvalidPaymentMoney() {
        UserApplication userApplication = new UserApplication("a");
        Payment p1 = new Payment("account", 102, card);

        given(userRepository.findByUsername("a")).willReturn(userApplication);
        Payment create = paymentService.savePayment(p1, "a");
    }

    /**
     * Test delete payment method
     * fail if method doesn't verify delete
     */
    @Test
    public void deletePayment() {
        final Long id = 1L;

        paymentService.deletePayment(id);
        paymentService.deletePayment(id);

        verify(paymentRepository, times(2)).deleteById(id);
    }
}
