package kr.hhplus.be.server.infra.inventory;

import kr.hhplus.be.server.domain.inventory.Inventory;
import kr.hhplus.be.server.domain.inventory.InventoryRepository;
import kr.hhplus.be.server.infra.inventory.jpa.InventoryEntity;
import kr.hhplus.be.server.infra.inventory.jpa.InventoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryJpaRepository inventoryJpaRepository;

    @Override
    public Inventory findByProductId(Long productId) {
        return InventoryEntity.toDomain(inventoryJpaRepository.findByProductId(productId));
    }

    @Override
    public Inventory save(Inventory inventory) {
        return InventoryEntity.toDomain(
                inventoryJpaRepository.save(
                        InventoryEntity.toEntity(inventory)
                )
        );
    }
}
