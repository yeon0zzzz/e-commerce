package kr.hhplus.be.server.domain.payment;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("결제_도메인_유효성_검증_단위_테스트")
public class PaymentDomainTest {

    @Test
    @DisplayName("결제_성공_금액_일치")
    void completeSuccess() {
        // given
        BigDecimal amount = BigDecimal.valueOf(5000);
        Payment payment = basePayment(PaymentStatus.PENDING, amount);

        // when
        Payment completed = payment.complete(amount);

        // then
        assertThat(completed.status()).isEqualTo(PaymentStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제_실패_금액_불일치")
    void complete_fail_mismatch() {
        // given
        BigDecimal paid = BigDecimal.valueOf(5000);
        BigDecimal expected = BigDecimal.valueOf(3000);
        Payment payment = basePayment(PaymentStatus.PENDING, paid);

        // then
        assertThatThrownBy(() -> payment.complete(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PaymentMessage.MISMATCH_AMOUNT);
    }

    @Test
    @DisplayName("결제_가능_상태_검사_PENDING이면_통과")
    void validatePayableSuccess() {
        Payment payment = basePayment(PaymentStatus.PENDING, BigDecimal.valueOf(1000));

        assertThatCode(payment::validatePayable)
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("결제_가능_상태_검사_SUCCESS면_예외")
    void validatePayableFail() {
        Payment payment = basePayment(PaymentStatus.SUCCESS, BigDecimal.valueOf(1000));

        assertThatThrownBy(payment::validatePayable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(PaymentMessage.NOT_PAYABLE);
    }

    private Payment basePayment(PaymentStatus status, BigDecimal amount) {
        return Payment.builder()
                .paymentId(1L)
                .orderId(123L)
                .status(status)
                .method(PaymentMethod.POINT)
                .paidAmount(amount)
                .paidAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
