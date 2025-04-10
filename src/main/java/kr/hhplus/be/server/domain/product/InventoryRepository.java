package kr.hhplus.be.server.domain.product;

public interface InventoryRepository {
    Inventory findByProductId(Long productId);

    void save(Inventory inventory);

    void deductStock(Long productId, long quantity); // 재고 차감 등 비즈니스 메서드로 확장 가능
}
