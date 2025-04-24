package kr.hhplus.be.server.domain.stock;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@DisplayName("재고_도메인_유효성_검증_단위_테스트")
public class StockDomainTest {

    @Test
    @DisplayName("사용_가능_재고_계산")
    void availableProductStock() {
        Stock stock = Stock.builder()
                .stockId(1L)
                .productId(1L)
                .quantity(1)
                .updatedAt(LocalDateTime.now())
                .build();

        assertThat(stock.quantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("요청_수량_초과시_예외발생")
    void quantityExceedThrowException() {
        Stock stock = Stock.builder()
                .stockId(1L)
                .productId(1L)
                .quantity(1)
                .updatedAt(LocalDateTime.now())
                .build();

        assertThatThrownBy(() -> stock.validateStockEnough(10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(StockMessage.STOCK_NOT_ENOUGH);
    }

}
