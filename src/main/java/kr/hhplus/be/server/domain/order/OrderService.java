package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventRepository orderEventRepository;

    public Order createOrder(Long userId, List<OrderItem> items, BigDecimal discountAmount) {
        BigDecimal totalAmount = calculateTotalAmount(items);
        BigDecimal finalAmount = totalAmount.subtract(discountAmount).max(BigDecimal.ZERO);

        // 주문 객체 생성
        Order order = Order.builder()
                .userId(userId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 저장
        Order savedOrder = orderRepository.save(order);

        // 항목 저장
        List<OrderItem> orderItems = items.stream()
                .map(item -> OrderItem.builder()
                        .orderId(savedOrder.orderId())
                        .productId(item.productId())
                        .quantity(item.quantity())
                        .price(item.price())
                        .totalPrice(OrderItem.calculateTotal(item.price(), item.quantity()))
                        .build())
                .toList();
        orderItemRepository.saveAll(orderItems);

        // 주문 생성 이벤트 저장
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(savedOrder.orderId())
                .status(OrderStatus.CREATED)
                .changedAt(LocalDateTime.now())
                .build();
        orderEventRepository.save(orderEvent);

        return savedOrder;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}