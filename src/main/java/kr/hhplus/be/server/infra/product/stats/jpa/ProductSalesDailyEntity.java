package kr.hhplus.be.server.infra.product.stats.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.product.ProductStatus;
import kr.hhplus.be.server.domain.product.stats.ProductSalesDaily;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_sales_daily")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ProductSalesDailyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sales_at")
    private LocalDateTime salesAt;

    @Column(name = "quantity")
    private Long quantity;

    public static ProductSalesDailyEntity toEntity(ProductSalesDaily sales) {
        return ProductSalesDailyEntity.builder()
                .productId(sales.productId())
                .salesAt(sales.salesAt())
                .quantity(sales.quantity())
                .build();
    }

    public static ProductSalesDaily toDomain(ProductSalesDailyEntity entity) {
        return ProductSalesDaily.builder()
                .productId(entity.getProductId())
                .salesAt(entity.getSalesAt())
                .quantity(entity.getQuantity())
                .build();
    }
}
