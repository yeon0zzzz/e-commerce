package kr.hhplus.be.server.domain.inventory;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.IvParameterSpec;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class InventoryServiceIntegrationTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("재고_조회")
    void findInventorySuccess() {
        Inventory inventory = Inventory.builder()
                .inventoryId(null)
                .productId(1L)
                .quantity(1)
                .reservedQuantity(0)
                .updatedAt(LocalDateTime.now())
                .build();
        Inventory savedInventory = inventoryService.save(inventory);

        Inventory result = inventoryService.findByProductId(savedInventory.productId());
        assertThat(result.inventoryId()).isEqualTo(1L);
        assertThat(result.quantity()).isEqualTo(1);
        assertThat(result.reservedQuantity()).isEqualTo(0);
    }
}
