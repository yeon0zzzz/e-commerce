package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventRepository orderEventRepository;

    public Order create(Long userId, List<OrderItem> items, BigDecimal discountAmount) {
        BigDecimal totalAmount = OrderItem.sumTotalPrice(items);

        // 주문 객체 생성
        Order order = Order.create(userId, totalAmount, discountAmount);

        // 저장
        Order savedOrder = orderRepository.save(order);

        // 항목 저장
        List<OrderItem> orderItems = items.stream()
                .map(item -> OrderItem.create(item.productId(), item.quantity(), item.price()))
                .toList();
        orderItemRepository.saveAll(orderItems);

        // 주문 생성 이벤트 저장
//        OrderEvent orderEvent = OrderEvent.builder()
//                .orderId(savedOrder.orderId())
//                .status(Order.OrderStatus.CREATED)
//                .changedAt(LocalDateTime.now())
//                .build();
//        orderEventRepository.save(orderEvent);

        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}