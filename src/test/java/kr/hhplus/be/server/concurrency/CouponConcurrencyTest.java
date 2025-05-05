package kr.hhplus.be.server.concurrency;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("선착순 쿠폰 발급을 동시에 요청시 모든 요청에 대해 발급 되어야 한다.")
    void issueCouponConcurrencyFailTest() throws InterruptedException {

        int threadCount = 100;

        Coupon coupon = Coupon.builder()
                .couponId(null)
                .name("동시성 쿠폰")
                .discountAmount(BigDecimal.valueOf(1000))
                .issuedQuantity(threadCount)
                .issuedCount(0)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(1))
                .expiredAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Coupon savedCoupon = couponService.save(coupon);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    couponService.issue(savedCoupon.couponId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Coupon result = couponService.findById(savedCoupon.couponId());

        // 0 + (100) = 100
        assertThat(result.issuedCount()).isEqualTo(threadCount);

    }
}
