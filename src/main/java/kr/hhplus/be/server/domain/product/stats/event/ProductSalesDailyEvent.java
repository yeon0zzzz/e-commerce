package kr.hhplus.be.server.domain.product.stats.event;

import kr.hhplus.be.server.domain.order.OrderItem;

import java.util.List;

public record ProductSalesDailyEvent(List<OrderItem> items) {}
