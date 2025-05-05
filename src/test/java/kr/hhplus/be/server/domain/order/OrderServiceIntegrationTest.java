package kr.hhplus.be.server.domain.order;

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
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("주문_조회")
    void findOrderSuccess() {
        Order order = Order.builder()
                .orderId(null)
                .userId(1L)
                .totalAmount(BigDecimal.valueOf(1000L))
                .discountAmount(BigDecimal.valueOf(500L))
                .finalAmount(BigDecimal.valueOf(500L))
                .status(Order.OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepository.save(order);

        Order result = orderService.findById(savedOrder.orderId());

        assertThat(result.orderId()).isEqualTo(1L);
        assertThat(result.finalAmount()).isEqualTo(BigDecimal.valueOf(500L));
    }
}
