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
    void paySuccess() {
        // given
        Long orderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(5000);
        PaymentMethod method = PaymentMethod.POINT;

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        given(paymentRepository.save(any(Payment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        paymentService.pay(orderId, amount, method);

        // then
        verify(paymentRepository).save(captor.capture());
        Payment saved = captor.getValue();

        assertThat(saved.orderId()).isEqualTo(orderId);
        assertThat(saved.paidAmount()).isEqualByComparingTo(amount);
        assertThat(saved.method()).isEqualTo(method);
        assertThat(saved.paidAt()).isNotNull();
    }
}