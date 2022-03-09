package com.kord.ssm.config.actions;

import com.kord.ssm.domain.PaymentEvent;
import com.kord.ssm.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class PreAuthDeclinedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        System.out.println("Sending notification declined");
    }
}
