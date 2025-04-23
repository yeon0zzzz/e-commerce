package kr.hhplus.be.server.infra.inventory.jpa;

import kr.hhplus.be.server.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, Long> {

    InventoryEntity findByProductId(Long productId);
}
