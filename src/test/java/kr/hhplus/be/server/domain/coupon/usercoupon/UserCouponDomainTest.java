package kr.hhplus.be.server.domain.coupon.usercoupon;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@DisplayName("사용자_쿠폰_도메인_유효성_검증_단위_테스트")
public class UserCouponDomainTest {

    //give
    private final UserCoupon userCoupon = UserCoupon.builder()
            .userCouponId(1L)
            .userId(1L)
            .couponId(1L)
            .used(true)
            .usedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("사용된_쿠폰_예외처리_테스트")
    void validateUsableThrowException() {
        assertThatThrownBy(userCoupon::validateUsable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserCouponMessage.USED_COUPON);
    }
}
