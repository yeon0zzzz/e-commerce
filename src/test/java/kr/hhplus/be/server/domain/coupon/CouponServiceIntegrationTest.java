package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class CouponServiceIntegrationTest {

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
    @DisplayName("쿠폰_조회")
    void findCouponSuccess() {
        Coupon coupon = Coupon.builder()
                .couponId(null)
                .name("Coupon1")
                .discountAmount(BigDecimal.valueOf(1000L))
                .issuedQuantity(100)
                .issuedCount(0)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(2))
                .expiredAt(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now().minusDays(3))
                .updatedAt(LocalDateTime.now())
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);

        Coupon result = couponService.findById(savedCoupon.couponId());
        assertThat(result.issuedCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿠폰_발급_성공")
    void issueCouponSuccess() {
        Coupon coupon = Coupon.builder()
                .couponId(null)
                .name("Coupon1")
                .discountAmount(BigDecimal.valueOf(1000L))
                .issuedQuantity(1)
                .issuedCount(0)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(2))
                .expiredAt(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now().minusDays(3))
                .updatedAt(LocalDateTime.now())
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);

        Coupon result = couponService.issue(savedCoupon.couponId());
        assertThat(result.issuedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰_발급_수량_한도_초과_실패")
    void issueCouponFail() {
        Coupon coupon = Coupon.builder()
                .couponId(null)
                .name("Coupon1")
                .discountAmount(BigDecimal.valueOf(1000L))
                .issuedQuantity(1)
                .issuedCount(1)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(2))
                .expiredAt(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now().minusDays(3))
                .updatedAt(LocalDateTime.now())
                .build();
        Coupon result = couponRepository.save(coupon);

        assertThatThrownBy(() -> couponService.issue(result.couponId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CouponMessage.EXCEEDED_LIMIT_COUPON);
    }

}