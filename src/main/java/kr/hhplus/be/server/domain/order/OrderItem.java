package kr.hhplus.be.server.domain.order;


import lombok.Builder;

import java.math.BigDecimal;

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
}