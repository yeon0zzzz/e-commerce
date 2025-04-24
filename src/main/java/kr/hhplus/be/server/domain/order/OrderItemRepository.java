package kr.hhplus.be.server.domain.order;

import java.util.List;

public interface OrderItemRepository {
    void saveAll(List<OrderItem> orderItems);
    List<OrderItem> findByOrderId(Long orderId);
}
