package kr.hhplus.be.server.domain.coupon.usercoupon;

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
public class UserCouponServiceIntegrationTest {

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Test
    @DisplayName("사용자_쿠폰_조회")
    void findUserCouponSuccess() {
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(null)
                .userId(1L)
                .couponId(1L)
                .used(false)
                .usedAt(null)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        UserCoupon result = userCouponService.findById(savedUserCoupon.userCouponId());
        assertThat(result.couponId()).isEqualTo(1L);
        assertThat(result.used()).isEqualTo(false);
    }

    @Test
    @DisplayName("사용자_쿠폰_사용_성공_테스트")
    void useSuccess() {
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(null)
                .userId(1L)
                .couponId(1L)
                .used(false)
                .usedAt(null)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        UserCoupon result = userCouponService.findById(savedUserCoupon.userCouponId());
        UserCoupon useCoupon = userCouponService.use(result.userCouponId());

        assertThat(useCoupon.couponId()).isEqualTo(1L);
        assertThat(useCoupon.used()).isEqualTo(true);
    }

    @Test
    @DisplayName("사용자_쿠폰_사용_실패_테스트")
    void useFail() {
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(null)
                .userId(1L)
                .couponId(1L)
                .used(true)
                .usedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        UserCoupon result = userCouponService.findById(savedUserCoupon.userCouponId());

        assertThatThrownBy(() -> userCouponService.use(result.userCouponId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserCouponMessage.USED_COUPON);
    }
}
