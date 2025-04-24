package kr.hhplus.be.server.domain.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment pay(Long orderId, BigDecimal amount) {

        Payment payment = Payment.complete(orderId, amount);

        return paymentRepository.save(payment);
    }

    public Payment cancel(Payment payment) {

        Payment cancelledPayment = payment.cancel();

        return paymentRepository.save(cancelledPayment);
    }

    @Transactional(readOnly = true)
    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}