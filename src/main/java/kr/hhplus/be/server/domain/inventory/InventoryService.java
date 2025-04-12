package kr.hhplus.be.server.domain.inventory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        return inventory;
    }

    public void validateStockEnough(Long productId, Long requestedQuantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        inventory.validateStockEnough(requestedQuantity);
    }
}
