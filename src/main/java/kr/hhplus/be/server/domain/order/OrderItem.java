package kr.hhplus.be.server.domain.order;


import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderItem(
        Long orderItemId,
        Long orderId,
        Long productId,
        Long quantity,
        BigDecimal price,
        BigDecimal totalPrice
) {
    public static BigDecimal calculateTotal(BigDecimal price, long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static BigDecimal sumTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderItem create(Long productId, Long quantity, BigDecimal price) {
        return OrderItem.builder()
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .totalPrice(calculateTotal(price, quantity))
                .build();
    }

    public static OrderItem create(Long orderId, Long productId, Long quantity, BigDecimal price) {
        return OrderItem.builder()
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .totalPrice(calculateTotal(price, quantity))
                .build();
    }
}