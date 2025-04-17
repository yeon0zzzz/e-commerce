package kr.hhplus.be.server.domain.inventory;

import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public void validateStockEnough(Long productId, Long requestedQuantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        inventory.validateStockEnough(requestedQuantity);
    }

    public void reserve(Long productId, Long requestedQuantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId);

        Inventory reservedInventory = inventory.reserve(requestedQuantity);

        inventoryRepository.save(reservedInventory);
    }

    public void reserveAll(List<OrderItem> items) {
        items.forEach(item -> reserve(item.productId(), item.quantity()));
    }

    public Inventory deduct(Long productId, Long requestedQuantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId);

        Inventory deductInventory = inventory.deduct(requestedQuantity);

        inventoryRepository.save(deductInventory);

        return deductInventory;
    }
}
