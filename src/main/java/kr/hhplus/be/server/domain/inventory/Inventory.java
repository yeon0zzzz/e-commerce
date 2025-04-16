package kr.hhplus.be.server.domain.inventory;

import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

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

    public Inventory reserve(Long requested) {

        validateStockEnough(requested);

        return Inventory.builder()
                .inventoryId(this.inventoryId)
                .productId(this.productId)
                .quantity(this.quantity)
                .reservedQuantity(this.reservedQuantity + requested)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Inventory deduct(Long requested) {

        if (requested > quantity) {
            throw new IllegalStateException(InventoryMessage.STOCK_NOT_ENOUGH);
        }

        return Inventory.builder()
                .inventoryId(this.inventoryId)
                .productId(this.productId)
                .quantity(this.quantity - requested)
                .reservedQuantity(this.reservedQuantity - requested)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
