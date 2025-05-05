package kr.hhplus.be.server.domain.popular;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PopularProduct(
        Long productId,
        String name,
        BigDecimal price,
        Long totalQuantity
) {}
