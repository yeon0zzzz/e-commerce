package kr.hhplus.be.server.domain.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


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

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        given(paymentRepository.save(any(Payment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        paymentService.request(orderId, amount, method);

        // then
        verify(paymentRepository).save(captor.capture());
        Payment savedPayment = captor.getValue();

        assertThat(savedPayment.status()).isEqualTo(PaymentStatus.PENDING);
        assertThat(savedPayment.paidAmount()).isEqualByComparingTo(amount);
        assertThat(savedPayment.method()).isEqualTo(method);
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

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        given(paymentRepository.findByOrderId(orderId)).willReturn(pending);
        given(paymentRepository.save(any(Payment.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        paymentService.complete(orderId, amount);

        // then
        verify(paymentRepository).save(captor.capture());
        Payment savedPayment = captor.getValue();

        assertThat(savedPayment.status()).isEqualTo(PaymentStatus.SUCCESS);
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
        assertThatThrownBy(() -> paymentService.complete(orderId, wrong))
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
        assertThatThrownBy(() -> paymentService.complete(orderId, amount))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(PaymentMessage.NOT_PAYABLE);
    }
}