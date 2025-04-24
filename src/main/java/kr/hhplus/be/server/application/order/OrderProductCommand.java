package kr.hhplus.be.server.application.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderProductCommand(
        Long productId,
        Long quantity,
        BigDecimal price
) {}
