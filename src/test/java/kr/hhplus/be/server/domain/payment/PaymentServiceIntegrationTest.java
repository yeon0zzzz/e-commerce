package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponRepository;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import kr.hhplus.be.server.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("결제_조회")
    void findPaymentSuccess() {
        Payment payment = Payment.builder()
                .paymentId(null)
                .orderId(1L)
                .paidAmount(BigDecimal.valueOf(1000L))
                .paidAt(LocalDateTime.now())
                .build();
        Payment savedPayment = paymentRepository.save(payment);

        Payment result = paymentService.findByOrderId(savedPayment.orderId());

        assertThat(result.paymentId()).isEqualTo(1L);
        assertThat(result.paidAmount()).isEqualTo(BigDecimal.valueOf(1000L));
    }
}
