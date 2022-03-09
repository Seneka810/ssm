package com.kord.ssm.service;

import com.kord.ssm.domain.Payment;
import com.kord.ssm.domain.PaymentEvent;
import com.kord.ssm.domain.PaymentState;
import com.kord.ssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @RepeatedTest(10)
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuth = paymentService.preAuth(savedPayment.getId());

        if(preAuth.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> auth = paymentService.authorizePayment(savedPayment.getId());

            System.out.println("Result of Auth: " + auth.getState().getId());
        } else {
            System.out.println("Payment failed pre-auth");
        }

    }
}