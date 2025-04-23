package kr.hhplus.be.server.domain.stock;


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
public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @Test
    @DisplayName("재고_조회_성공")
    void getStock() {
        // given
        Stock stock = Stock.builder()
                .stockId(1L)
                .productId(1L)
                .quantity(1)
                .updatedAt(LocalDateTime.now())
                .build();

        given(stockRepository.findByProductId(1L)).willReturn(stock);

        // when
        Stock result = stockService.findByProductId(1L);

        // then
        assertThat(result.quantity()).isEqualTo(1);
        verify(stockRepository).findByProductId(1L);
    }

    @Test
    @DisplayName("재고_조회_실패")
    void getsStockFailed() {
        // given
        given(stockRepository.findByProductId(999L))
                .willThrow(new IllegalArgumentException(StockMessage.STOCK_NOT_FOUND));

        // when
        // then
        assertThatThrownBy(() -> stockService.findByProductId(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StockMessage.STOCK_NOT_FOUND);
    }
}
