package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.OrderItem;

import java.util.List;

public record OrderCompletedEvent(List<OrderItem> items) {}
