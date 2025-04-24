package kr.hhplus.be.server.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Test
    @DisplayName("재고_조회")
    void findStockSuccess() {
        Stock stock = Stock.builder()
                .stockId(null)
                .productId(1L)
                .quantity(1)
                .updatedAt(LocalDateTime.now())
                .build();
        Stock savedstock = stockService.save(stock);

        Stock result = stockService.findByProductId(savedstock.productId());
        assertThat(result.stockId()).isEqualTo(1L);
        assertThat(result.quantity()).isEqualTo(1);
    }
}
