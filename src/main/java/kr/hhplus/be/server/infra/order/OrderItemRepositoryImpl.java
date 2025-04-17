package kr.hhplus.be.server.infra.order;

import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
    @Override
    public void saveAll(List<OrderItem> orderItems) {

    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return List.of();
    }
}
