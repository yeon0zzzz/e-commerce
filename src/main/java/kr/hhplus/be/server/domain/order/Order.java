package kr.hhplus.be.server.domain.order;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Order(
        Long orderId,
        Long userId,
        BigDecimal totalAmount,
        BigDecimal discountAmount,
        BigDecimal finalAmount,
        Long usedPoint,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public void validatePayable() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(OrderMessage.NOT_PAYABLE_STATUS);
        }
    }

    public BigDecimal calculateFinalAmount() {
        return totalAmount.subtract(discountAmount).max(BigDecimal.ZERO);
    }
}