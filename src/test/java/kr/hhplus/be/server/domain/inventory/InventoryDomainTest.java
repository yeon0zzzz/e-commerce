package kr.hhplus.be.server.domain.inventory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@DisplayName("재고_도메인_유효성_검증_단위_테스트")
public class InventoryDomainTest {

    @Test
    @DisplayName("사용_가능_재고_계산")
    void availableProductInventory() {
        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(100L)
                .quantity(50)
                .reservedQuantity(20)
                .updatedAt(LocalDateTime.now())
                .build();

        assertThat(inventory.availableQuantity()).isEqualTo(30);
    }

    @Test
    @DisplayName("요청_수량_초과시_예외발생")
    void quantityExceedThrowException() {
        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(100L)
                .quantity(10)
                .reservedQuantity(5)
                .updatedAt(LocalDateTime.now())
                .build();

        assertThatThrownBy(() -> inventory.validateStockEnough(10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(InventoryMessage.STOCK_NOT_ENOUGH);
    }

}
