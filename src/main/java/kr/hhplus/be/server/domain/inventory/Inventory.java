package kr.hhplus.be.server.domain.inventory;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Inventory(
        Long inventoryId,
        Long productId,
        long quantity,
        long reservedQuantity,
        LocalDateTime updatedAt
) {

    public long availableQuantity() {
        return quantity - reservedQuantity;
    }

    public void validateStockEnough(long requested) {
        if (availableQuantity() < requested) {
            throw new IllegalStateException(InventoryMessage.STOCK_NOT_ENOUGH);
        }
    }
}
