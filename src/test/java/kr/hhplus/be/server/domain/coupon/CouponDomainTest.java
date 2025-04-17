package kr.hhplus.be.server.domain.coupon;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("쿠폰_도메인_유효성_검증_단위_테스트")
public class CouponDomainTest {

    private final Coupon coupon = Coupon.builder()
            .couponId(1L)
            .name("100won Coupon")
            .discountAmount(BigDecimal.valueOf(100L))
            .issuedQuantity(1000)
            .issuedCount(1000)
            .couponStatus(CouponStatus.EXPIRED)
            .activatedAt(LocalDateTime.now().minusDays(2))
            .expiredAt(LocalDateTime.now().minusDays(1))
            .createdAt(LocalDateTime.now().minusDays(3))
            .updatedAt(LocalDateTime.now())
            .build();


    @Test
    @DisplayName("쿠폰_상태_활성화_예외처리_테스트")
    void validateActiveThrowException() {
        assertThatThrownBy(coupon::validateActive)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CouponMessage.INVALID_COUPON);
    }

    @Test
    @DisplayName("쿠폰_발급_한도_예외처리_테스트")
    void validateIssueCountThrowException() {
        assertThatThrownBy(coupon::validateIssueCount)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CouponMessage.EXCEEDED_LIMIT_COUPON);
    }

    @Test
    @DisplayName("쿠폰_사용_기간_예외처리_테스트")
    void validateExpiredThrowException() {
        assertThatThrownBy(() -> coupon.validateExpired(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CouponMessage.NOT_IN_PERIOD);
    }
}
