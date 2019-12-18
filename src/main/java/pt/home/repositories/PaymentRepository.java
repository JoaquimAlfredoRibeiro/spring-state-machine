package pt.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.home.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
