package kr.hhplus.be.server.infra.order.jpa;

import kr.hhplus.be.server.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long orderId);
}
