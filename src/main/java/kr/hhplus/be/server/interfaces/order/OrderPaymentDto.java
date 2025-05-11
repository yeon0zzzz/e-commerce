package kr.hhplus.be.server.interfaces.order;

import kr.hhplus.be.server.application.order.OrderPaymentCommand;
import kr.hhplus.be.server.application.order.OrderProductCommand;
import kr.hhplus.be.server.domain.order.Order;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderPaymentDto {

    @Builder
    public record Request(
            Long userId,
            List<ProductRequest> items,
            Long userCouponId,
            BigDecimal discountAmount
    ) {
        public OrderPaymentCommand toCommand() {
            return OrderPaymentCommand.builder()
                    .userId(userId)
                    .items(items.stream()
                            .map(item -> OrderProductCommand.builder()
                                    .productId(item.productId())
                                    .quantity(item.quantity())
                                    .price(item.price())
                                    .build())
                            .toList())
                    .userCouponId(userCouponId)
                    .discountAmount(discountAmount)
                    .build();
        }
    }

    @Builder
    public record Response(
            Long orderId,
            BigDecimal finalAmount,
            LocalDateTime createdAt
    ) {
        public static Response of(Order order) {
            return Response.builder()
                    .orderId(order.orderId())
                    .finalAmount(order.finalAmount())
                    .createdAt(order.createdAt())
                    .build();
        }
    }

    public record ProductRequest(
            Long productId,
            Long quantity,
            BigDecimal price
    ) {}
}