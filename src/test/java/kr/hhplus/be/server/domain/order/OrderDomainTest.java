package kr.hhplus.be.server.domain.order;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("주문_도메인_유효성_검증_단위_테스트")
public class OrderDomainTest {

    @Test
    @DisplayName("주문_상태가_CREATED일_경우_결제_가능")
    void validatePayableSuccess() {
        Order order = createOrder(OrderStatus.CREATED);
        assertThatCode(order::validatePayable).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문_상태가_PAID일_경우_결제_불가")
    void validatePayableFail() {
        Order order = createOrder(OrderStatus.PAID);
        assertThatThrownBy(order::validatePayable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(OrderMessage.NOT_PAYABLE_STATUS);
    }

    @Test
    @DisplayName("최종_결제_금액_계산")
    void calculateFinalAmountSuccess() {
        Order order = createOrder(OrderStatus.CREATED);
        assertThat(order.calculateFinalAmount()).isEqualByComparingTo("8000");
    }

    private Order createOrder(OrderStatus status) {
        return Order.builder()
                .orderId(1L)
                .userId(1L)
                .totalAmount(BigDecimal.valueOf(10000))
                .discountAmount(BigDecimal.valueOf(2000))
                .finalAmount(BigDecimal.valueOf(8000))
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
