package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StockConcurrencyTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("재고 차감을 요청시 모든 요청에 대한 재고 차감이 이루어져야 한다.")
    void deductStockConcurrencyFailTest() throws InterruptedException {
        int threadCount = 100;

        Stock stock = Stock.builder()
                .stockId(null)
                .productId(1L)
                .quantity(threadCount)
                .updatedAt(LocalDateTime.now())
                .build();
        Stock savedStock = stockService.save(stock);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    stockService.deduct(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock result = stockService.findByProductId(savedStock.productId());

        // 100 - (100) = 0
        assertThat(result.quantity()).isEqualTo(0);
    }
}
