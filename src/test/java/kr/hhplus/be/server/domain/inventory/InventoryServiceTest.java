package kr.hhplus.be.server.domain.inventory;


import kr.hhplus.be.server.domain.inventory.InventoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("재고_서비스_단위_테스트")
public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("재고_조회_성공")
    void getInventory() {
        // given
        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .quantity(100)
                .reservedQuantity(40)
                .updatedAt(LocalDateTime.now())
                .build();

        given(inventoryRepository.findByProductId(1L)).willReturn(inventory);

        // when
        Inventory result = inventoryService.findByProductId(1L);

        // then
        assertThat(result.availableQuantity()).isEqualTo(60);
        verify(inventoryRepository).findByProductId(1L);
    }

    @Test
    @DisplayName("재고_조회_실패")
    void getInventoryFailed() {
        // given
        given(inventoryRepository.findByProductId(999L))
                .willThrow(new IllegalArgumentException(InventoryMessage.INVENTORY_NOT_FOUND));

        // when
        // then
        assertThatThrownBy(() -> inventoryService.findByProductId(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(InventoryMessage.INVENTORY_NOT_FOUND);
    }
}
