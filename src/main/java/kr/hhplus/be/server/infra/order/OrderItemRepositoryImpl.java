package kr.hhplus.be.server.infra.order;

import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderItemRepository;
import kr.hhplus.be.server.infra.order.jpa.OrderItemEntity;
import kr.hhplus.be.server.infra.order.jpa.OrderItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public void saveAll(List<OrderItem> orderItems) {
        orderItemJpaRepository.saveAll(orderItems.stream()
                .map(OrderItemEntity::toEntity)
                .toList());
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemJpaRepository.findByOrderId(orderId).stream()
                .map(OrderItemEntity::toDomain)
                .toList();
    }
}
