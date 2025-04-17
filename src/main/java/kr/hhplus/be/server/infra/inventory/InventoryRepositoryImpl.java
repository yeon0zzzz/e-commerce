package kr.hhplus.be.server.infra.inventory;

import kr.hhplus.be.server.domain.inventory.Inventory;
import kr.hhplus.be.server.domain.inventory.InventoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    @Override
    public Inventory findByProductId(Long productId) {
        return null;
    }

    @Override
    public void save(Inventory inventory) {

    }

    @Override
    public void deductStock(Long productId, long quantity) {

    }
}
