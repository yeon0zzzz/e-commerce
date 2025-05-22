package kr.hhplus.be.server.application.event;

import kr.hhplus.be.server.domain.order.OrderItem;

import java.util.List;

public record ProductSalesDailyEvent(List<OrderItem> items) {}
