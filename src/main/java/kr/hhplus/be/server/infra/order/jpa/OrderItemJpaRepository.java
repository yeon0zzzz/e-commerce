package kr.hhplus.be.server.infra.order.jpa;

import kr.hhplus.be.server.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrderId(Long orderId);
}
