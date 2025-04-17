package kr.hhplus.be.server.infra.order;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.order.OrderEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderEventRepositoryImpl implements OrderEventRepository {
    @Override
    public void save(OrderEvent event) {

    }

    @Override
    public List<OrderEvent> findByOrderId(Long orderId) {
        return List.of();
    }
}
