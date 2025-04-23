package kr.hhplus.be.server.domain.stock;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Stock(
        Long stockId,
        Long productId,
        long quantity,
        LocalDateTime updatedAt
) {

    public void validateStockEnough(long requestedQuantity) {
        if (requestedQuantity > quantity) {
            throw new IllegalStateException(StockMessage.STOCK_NOT_ENOUGH);
        }
    }

    public Stock deduct(long requestedQuantity) {
        validateStockEnough(requestedQuantity);

        return Stock.builder()
                .stockId(this.stockId)
                .productId(this.productId)
                .quantity(this.quantity - requestedQuantity)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
