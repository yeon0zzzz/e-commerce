package kr.hhplus.be.server.domain.product.stats;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductSalesDaily(
        LocalDateTime salesAt,
        Long productId,
        Long quantity
) {

    public static ProductSalesDaily create(LocalDateTime salesAt, Long productId, Long quantity) {
        return ProductSalesDaily.builder()
                .salesAt(salesAt)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

}
