package kr.hhplus.be.server.domain.order;


import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record OrderEvent(
        Long orderEventId,
        Long orderId,
        OrderStatus status,
        LocalDateTime changedAt
) {
    public enum OrderStatus {
        CREATED,
        PAID,
        CANCELED,
        COMPLETED
    }
}