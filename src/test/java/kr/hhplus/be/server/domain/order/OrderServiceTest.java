package kr.hhplus.be.server.domain.order;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("주문_서비스_단위_테스트")
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderEventRepository orderEventRepository;

    @Test
    @DisplayName("주문_생성_성공_테스트")
    void createOrderSuccess() {
        // given
        Long userId = 1L;
        BigDecimal discountAmount = BigDecimal.valueOf(2000);
        long usedPoint = 0;

        OrderItem item1 = OrderItem.builder()
                .productId(100L)
                .quantity(2L)
                .price(BigDecimal.valueOf(5000))
                .totalPrice(BigDecimal.valueOf(10000))
                .build();

        Order fakeOrder = Order.builder()
                .orderId(999L)
                .userId(userId)
                .totalAmount(BigDecimal.valueOf(10000))
                .discountAmount(discountAmount)
                .finalAmount(BigDecimal.valueOf(8000))
                .status(Order.OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(orderRepository.save(any(Order.class))).willReturn(fakeOrder);

        // when
        Order result = orderService.create(userId, List.of(item1), discountAmount);

        // then
        assertThat(result.orderId()).isEqualTo(999L);
        assertThat(result.totalAmount()).isEqualByComparingTo("10000");
        assertThat(result.finalAmount()).isEqualByComparingTo("8000");

        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).saveAll(anyList());
//        verify(orderEventRepository).save(any(OrderEvent.class));
    }
}
