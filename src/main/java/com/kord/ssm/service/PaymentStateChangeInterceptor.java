package com.kord.ssm.service;

import com.kord.ssm.domain.Payment;
import com.kord.ssm.domain.PaymentEvent;
import com.kord.ssm.domain.PaymentState;
import com.kord.ssm.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentStateChangeInterceptor(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state,
                               Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition,
                               StateMachine<PaymentState, PaymentEvent> stateMachine,
                               StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message).flatMap(msg ->
                        Optional.ofNullable((Long) msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                .ifPresent(paymentId -> {
            Payment payment = paymentRepository.getById(paymentId);
            payment.setState(state.getId());
            paymentRepository.save(payment);
        });
    }
}
