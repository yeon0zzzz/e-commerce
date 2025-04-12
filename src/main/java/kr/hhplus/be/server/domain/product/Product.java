package kr.hhplus.be.server.domain.product;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Product(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        ProductStatus productStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public void validateIsActive() {
        if (!ProductStatus.ACTIVE.equals(this.productStatus)) {
            throw new IllegalStateException(ProductMessage.INACTIVE_PRODUCT);
        }
    }

    public boolean isDiscontinued() {
        return ProductStatus.DISCONTINUED.equals(this.productStatus);
    }

    public boolean isActive() {
        return ProductStatus.ACTIVE.equals(this.productStatus);
    }
}