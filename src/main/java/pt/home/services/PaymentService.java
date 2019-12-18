package pt.home.services;

import org.springframework.statemachine.StateMachine;
import pt.home.domain.Payment;
import pt.home.domain.PaymentEvent;
import pt.home.domain.PaymentState;

public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authPayment(Long paymentId);
}
