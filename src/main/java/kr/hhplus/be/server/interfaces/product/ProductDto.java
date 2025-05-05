package kr.hhplus.be.server.interfaces.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductStatus;
import lombok.Builder;

import java.math.BigDecimal;

public class ProductDto {

    @Builder
    public record Response(
            Long productId,
            String name,
            BigDecimal price,
            ProductStatus productStatus
    ) {
        public static Response of(Product product) {
            return Response.builder()
                    .productId(product.productId())
                    .name(product.name())
                    .price(product.price())
                    .productStatus(product.productStatus())
                    .build();
        }
    }
}
