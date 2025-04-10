package kr.hhplus.be.server.domain.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    /**
     * 결제 요청 시 PENDING 상태로 저장
     */
    public Payment requestPayment(Long orderId, BigDecimal amount, PaymentMethod method) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .status(PaymentStatus.PENDING)
                .method(method)
                .paidAmount(amount)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    /**
     * 결제 완료 처리
     */
    public Payment completePayment(Long orderId, BigDecimal expectedAmount) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        payment.validatePayable(); // 상태 검증
        Payment completed = payment.complete(expectedAmount); // 금액 일치 및 상태 변경

        return paymentRepository.save(completed); // 갱신
    }
}