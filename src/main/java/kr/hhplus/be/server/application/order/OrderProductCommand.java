package kr.hhplus.be.server.application.order;

import java.math.BigDecimal;

public record OrderProductCommand(
        Long productId,
        Long quantity,
        BigDecimal price
) {}
