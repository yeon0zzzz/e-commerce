package kr.hhplus.be.server.domain.payment;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("결제_도메인_단위_테스트")
public class PaymentDomainTest {

    private Payment basePayment(Payment.PaymentStatus status) {
        return Payment.builder()
                .paymentId(1L)
                .orderId(1L)
                .amount(BigDecimal.valueOf(100L))
                .status(status)
                .paidAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("결제 취소 성공")
    void validateCancellableSuccess() {
        // given
        Payment payment = basePayment(Payment.PaymentStatus.PENDING);

        // when & then
        assertThatCode(payment::validateCancellable)
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("결제 취소 실패")
    void validateCancellableFail() {
        // given
        Payment payment = basePayment(Payment.PaymentStatus.FAILED);

        // when & then
        assertThatCode(payment::validateCancellable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(PaymentMessage.CANCEL_FAILED);
    }
}
