package kr.hhplus.be.server.interfaces.popular;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PopularProductDto(
        Long productId,
        String name,
        BigDecimal price,
        Long totalQuantity
) {
}
