package kr.hhplus.be.server.domain.product;

public interface InventoryRepository {
    Inventory findByProductId(Long productId);

    void save(Inventory inventory);

    void deductStock(Long productId, long quantity);
}
