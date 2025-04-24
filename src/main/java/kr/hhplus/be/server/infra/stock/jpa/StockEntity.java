package kr.hhplus.be.server.infra.stock.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.stock.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static StockEntity toEntity(Stock stock) {
        return StockEntity.builder()
                .stockId(stock.stockId())
                .productId(stock.productId())
                .quantity(stock.quantity())
                .updatedAt(stock.updatedAt())
                .build();
    }

    public static Stock toDomain (StockEntity entity){
        return Stock.builder()
                .stockId(entity.getStockId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
