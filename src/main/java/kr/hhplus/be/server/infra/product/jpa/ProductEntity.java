package kr.hhplus.be.server.infra.product.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .productId(product.productId())
                .name(product.name())
                .price(product.price())
                .productStatus(product.productStatus())
                .build();
    }

    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .productId(entity.getProductId())
                .name(entity.getName())
                .price(entity.getPrice())
                .productStatus(entity.getProductStatus())
                .build();
    }

}
