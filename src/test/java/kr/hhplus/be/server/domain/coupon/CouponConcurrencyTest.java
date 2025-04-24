package kr.hhplus.be.server.domain.coupon;

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

    @Test
    @DisplayName("쿠폰 발급 동시성 이슈 발생")
    void issueCouponConcurrencyFailTest() throws InterruptedException {

        Coupon coupon = Coupon.builder()
                .couponId(null)
                .name("동시성 쿠폰")
                .discountAmount(BigDecimal.valueOf(1000))
                .issuedQuantity(10)
                .issuedCount(0)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(1))
                .expiredAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Coupon savedCoupon = couponService.save(coupon);

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    couponService.issue(savedCoupon.couponId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Coupon result = couponService.findById(savedCoupon.couponId());
        assertThat(result.issuedCount()).isEqualTo(threadCount);

    }
}
