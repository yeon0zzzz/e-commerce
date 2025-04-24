package kr.hhplus.be.server.infra.order.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .orderItemId(orderItem.orderItemId())
                .orderId(orderItem.orderId())
                .productId(orderItem.productId())
                .quantity(orderItem.quantity())
                .price(orderItem.price())
                .totalPrice(orderItem.totalPrice())
                .build();
    }

    public static OrderItem toDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .orderItemId(entity.getOrderItemId())
                .orderId(entity.getOrderId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
}
