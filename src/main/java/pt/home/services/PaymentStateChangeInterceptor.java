package pt.home.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import pt.home.domain.Payment;
import pt.home.domain.PaymentEvent;
import pt.home.domain.PaymentState;
import pt.home.repositories.PaymentRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg ->
                                               {
                                                   Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                                                           .ifPresent(paymentId -> {
                                                               Payment payment = paymentRepository.getOne(paymentId);
                                                               payment.setPaymentState(state.getId());
                                                               paymentRepository.save(payment);
                                                           });
                                               });

    }
}
