package kr.hhplus.be.server.infra.payment;

import kr.hhplus.be.server.domain.payment.Payment;
import kr.hhplus.be.server.domain.payment.PaymentRepository;
import kr.hhplus.be.server.infra.payment.jpa.PaymentEntity;
import kr.hhplus.be.server.infra.payment.jpa.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment findByOrderId(Long orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(PaymentEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("결제정보가 없습니다."));
    }

    @Override
    public Payment save(Payment payment) {
        return PaymentEntity.toDomain(
                paymentJpaRepository.save(
                        PaymentEntity.toEntity(payment)
                )
        );
    }
}
