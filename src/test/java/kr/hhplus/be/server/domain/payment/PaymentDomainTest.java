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
    void validateAmountSuccess() {
        // given
        BigDecimal amount = BigDecimal.valueOf(5000);
        Payment payment = basePayment(amount);

        // when & then
        assertThatCode(() -> payment.validateAmount(amount))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("결제_실패_금액_불일치")
    void validateAmountFail() {
        // given
        BigDecimal actual = BigDecimal.valueOf(5000);
        BigDecimal expected = BigDecimal.valueOf(3000);
        Payment payment = basePayment(actual);

        // when & then
        assertThatThrownBy(() -> payment.validateAmount(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PaymentMessage.MISMATCH_AMOUNT);
    }

    private Payment basePayment(BigDecimal amount) {
        return Payment.builder()
                .paymentId(1L)
                .orderId(123L)
                .method(PaymentMethod.POINT)
                .paidAmount(amount)
                .paidAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
