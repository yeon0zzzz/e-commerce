package kr.hhplus.be.server.application.event.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderCompletedMessage(
        Long orderId,
        Long userId,
        BigDecimal totalAmount,
        BigDecimal finalAmount,
        List<OrderItemMessage> items
) {

    @JsonCreator
    public OrderCompletedMessage(
            @JsonProperty("orderId") Long orderId,
            @JsonProperty("userId") Long userId,
            @JsonProperty("totalAmount") BigDecimal totalAmount,
            @JsonProperty("finalAmount") BigDecimal finalAmount,
            @JsonProperty("items") List<OrderItemMessage> items
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.finalAmount = finalAmount;
        this.items = items;
    }

    public static OrderCompletedMessage from(Order order, List<OrderItem> orderItems) {
        List<OrderItemMessage> itemMessages = orderItems.stream()
                .map(i -> new OrderItemMessage(i.productId(), i.quantity(), i.price()))
                .toList();

        return new OrderCompletedMessage(
                order.orderId(),
                order.userId(),
                order.totalAmount(),
                order.finalAmount(),
                itemMessages
        );
    }

    public record OrderItemMessage(
            Long productId,
            Long quantity,
            BigDecimal price
    ) {

            @JsonCreator
            public OrderItemMessage(
                    @JsonProperty("productId") Long productId,
                    @JsonProperty("quantity") Long quantity,
                    @JsonProperty("price") BigDecimal price
            ) {
                this.productId = productId;
                this.quantity = quantity;
                this.price = price;
            }
        }
}
