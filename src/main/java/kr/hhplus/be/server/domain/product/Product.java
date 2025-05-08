package kr.hhplus.be.server.domain.product;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Product(
        Long productId,
        String name,
        BigDecimal price,
        ProductStatus productStatus
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

    public static Product create(String name, BigDecimal price) {
        return Product.builder()
                .name(name)
                .price(price)
                .productStatus(ProductStatus.ACTIVE)
                .build();
    }
}