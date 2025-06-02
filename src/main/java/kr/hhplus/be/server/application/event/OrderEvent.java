package kr.hhplus.be.server.application.event;

import kr.hhplus.be.server.domain.order.Order;

public class OrderEvent {

    public record Completed(Order order) {}

    public record Canceled(Order order) {}

    public record Failed(Order order, String reason) {}
}