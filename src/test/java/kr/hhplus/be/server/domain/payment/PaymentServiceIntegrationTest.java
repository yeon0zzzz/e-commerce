package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("결제_조회")
    void findPaymentSuccess() {
        Payment payment = Payment.builder()
                .paymentId(null)
                .orderId(1L)
                .amount(BigDecimal.valueOf(1000L))
                .status(Payment.PaymentStatus.COMPLETED)
                .paidAt(LocalDateTime.now())
                .build();
        Payment savedPayment = paymentRepository.save(payment);

        Payment result = paymentService.findByOrderId(savedPayment.orderId());

        assertThat(result.paymentId()).isEqualTo(1L);
        assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(1000L));
    }
}
