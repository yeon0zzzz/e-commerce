package kr.hhplus.be.server.domain.inventory;

public interface InventoryRepository {
    Inventory findByProductId(Long productId);

    Inventory save(Inventory inventory);
}
