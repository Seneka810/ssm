package com.kord.ssm.config.guards;

import com.kord.ssm.domain.PaymentEvent;
import com.kord.ssm.domain.PaymentState;
import com.kord.ssm.service.PaymentServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class PaymentIdGuard implements Guard<PaymentState, PaymentEvent> {
    @Override
    public boolean evaluate(StateContext<PaymentState, PaymentEvent> context) {
        return context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER) != null;
    }
}
