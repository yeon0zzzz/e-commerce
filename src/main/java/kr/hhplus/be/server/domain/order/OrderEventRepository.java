package kr.hhplus.be.server.domain.order;

import java.util.List;

public interface OrderEventRepository {
    void save(OrderEvent event);
    List<OrderEvent> findByOrderId(Long orderId);
}
