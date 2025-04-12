package kr.hhplus.be.server.domain.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@DisplayName("결제_서비스_단위_테스트")
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("결제_요청_성공")
    void requestPaymentSuccess() {
        // given
        Long orderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(5000);
        PaymentMethod method = PaymentMethod.POINT;

        given(paymentRepository.save(any(Payment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        Payment result = paymentService.requestPayment(orderId, amount, method);

        // then
        assertThat(result.status()).isEqualTo(PaymentStatus.PENDING);
        assertThat(result.paidAmount()).isEqualByComparingTo(amount);
        assertThat(result.method()).isEqualTo(method);
    }

    @Test
    @DisplayName("결제_완료_성공")
    void completePaymentSuccess() {
        // given
        Long orderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(5000);

        Payment pending = Payment.builder()
                .paymentId(100L)
                .orderId(orderId)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.POINT)
                .paidAmount(amount)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(paymentRepository.findByOrderId(orderId)).willReturn(pending);
        given(paymentRepository.save(any(Payment.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Payment completed = paymentService.completePayment(orderId, amount);

        // then
        assertThat(completed.status()).isEqualTo(PaymentStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제_금액_불일치_시_예외")
    void completePaymentFailMismatchAmount() {
        // given
        Long orderId = 1L;
        BigDecimal real = BigDecimal.valueOf(5000);
        BigDecimal wrong = BigDecimal.valueOf(3000);

        Payment pending = Payment.builder()
                .orderId(orderId)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.POINT)
                .paidAmount(real)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(paymentRepository.findByOrderId(orderId)).willReturn(pending);

        // expect
        assertThatThrownBy(() -> paymentService.completePayment(orderId, wrong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PaymentMessage.MISMATCH_AMOUNT);
    }

    @Test
    @DisplayName("결제_상태가_PENDING이_아닐_경우_예외")
    void completePaymentFailInvalidStatus() {
        // given
        Long orderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(5000);

        Payment paid = Payment.builder()
                .orderId(orderId)
                .status(PaymentStatus.SUCCESS)
                .method(PaymentMethod.POINT)
                .paidAmount(amount)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(paymentRepository.findByOrderId(orderId)).willReturn(paid);

        // expect
        assertThatThrownBy(() -> paymentService.completePayment(orderId, amount))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(PaymentMessage.NOT_PAYABLE);
    }
}