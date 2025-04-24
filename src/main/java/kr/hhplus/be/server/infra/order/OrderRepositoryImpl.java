package kr.hhplus.be.server.infra.order;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderRepository;
import kr.hhplus.be.server.infra.order.jpa.OrderEntity;
import kr.hhplus.be.server.infra.order.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .map(OrderEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("주문정보가 없습니다."));
    }

    @Override
    public Order save(Order order) {
        return OrderEntity.toDomain(
                orderJpaRepository.save(
                        OrderEntity.toEntity(order)
                )
        );
    }

}
