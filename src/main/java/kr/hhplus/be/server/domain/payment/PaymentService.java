package kr.hhplus.be.server.domain.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void pay(Long orderId, BigDecimal expectedAmount) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .paidAmount(expectedAmount)
                .paidAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }
}