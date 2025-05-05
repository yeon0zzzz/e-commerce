package kr.hhplus.be.server.domain.point;

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
@DisplayName("포인트 동시성 테스트")
public class PointConcurrencyTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("포인트 충전과 사용이 동시에 요청시 순차적으로 성공해야 한다.")
    void deductStockConcurrencyFailTest() throws InterruptedException {

        Point point = Point.builder()
                .pointId(null)
                .userId(1L)
                .point(100L)
                .updatedAt(LocalDateTime.now())
                .build();
        Point savedPoint = pointService.save(point);

        int threadCount = 1;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount*2);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    pointService.charge(1L, 5L);
                } finally {
                    latch.countDown();
                }
            });

            executor.submit(() -> {
                try {
                    pointService.use(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Point result = pointService.findByUserId(1L);

        // 100 + (5) = 105
        // 105 - (1) = 104
        assertThat(result.point()).isEqualTo(104);
    }
}
