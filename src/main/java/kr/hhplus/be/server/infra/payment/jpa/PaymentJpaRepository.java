package kr.hhplus.be.server.infra.payment.jpa;

import kr.hhplus.be.server.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByOrderId(Long orderId);
}
