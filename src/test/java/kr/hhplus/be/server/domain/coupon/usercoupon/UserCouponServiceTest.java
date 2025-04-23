package kr.hhplus.be.server.domain.coupon.usercoupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("사용자_쿠폰_서비스_단위_테스트")
class UserCouponServiceTest {

    @InjectMocks
    private UserCouponService userCouponService;

    @Mock
    private UserCouponRepository userCouponRepository;

    @Test
    @DisplayName("쿠폰_사용_테스트")
    void use() {
        Long userCouponId = 1L;
        Long userId = 1L;
        Long couponId = 1L;

        //give
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(userCouponId)
                .userId(userId)
                .couponId(couponId)
                .used(false)
                .usedAt(null)
                .createdAt(LocalDateTime.now())
                .build();

        given(userCouponRepository.findById(userCouponId)).willReturn(userCoupon);
        given(userCouponRepository.save(any(UserCoupon.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        UserCoupon result = userCouponService.use(userCouponId);

        //then
        assertThat(result.used()).isEqualTo(true);
        verify(userCouponRepository).findById(eq(userCouponId));
    }
}