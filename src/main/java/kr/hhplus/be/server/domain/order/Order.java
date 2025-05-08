package kr.hhplus.be.server.domain.order;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Order(
        Long orderId,
        Long userId,
        BigDecimal totalAmount,
        BigDecimal discountAmount,
        BigDecimal finalAmount,
        OrderStatus status,
        List<OrderItem> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public enum OrderStatus {
        CREATED,
        PAID,
        CANCELED,
        COMPLETED
    }

    public void validatePayable() {
        if (status != Order.OrderStatus.CREATED) {
            throw new IllegalStateException(OrderMessage.NOT_PAYABLE_STATUS);
        }
    }

    public static BigDecimal defaultDiscountAmount(BigDecimal discountAmount) {
        return discountAmount != null ? discountAmount : BigDecimal.ZERO;
    }

    public BigDecimal calculateFinalAmount() {
        return totalAmount.subtract(defaultDiscountAmount(discountAmount)).max(BigDecimal.ZERO);
    }

    public static Order create(Long userId, BigDecimal totalAmount, BigDecimal discountAmount, List<OrderItem> items) {
        return Order.builder()
                .userId(userId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalAmount(totalAmount.subtract(defaultDiscountAmount(discountAmount)).max(BigDecimal.ZERO))
                .status(OrderStatus.CREATED)
                .items(items)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}