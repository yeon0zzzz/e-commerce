package kr.hhplus.be.server.domain.coupon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("쿠폰_서비스_단위_테스트")
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰_발급_테스트")
    void issue() {
        Long couponId = 1L;

        //given
        Coupon coupon = Coupon.builder()
                .couponId(couponId)
                .name("100won Coupon")
                .discountAmount(BigDecimal.valueOf(100L))
                .issuedQuantity(1000)
                .issuedCount(0)
                .couponStatus(CouponStatus.ACTIVE)
                .activatedAt(LocalDateTime.now().minusDays(1))
                .expiredAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now().minusDays(2))
                .updatedAt(LocalDateTime.now())
                .build();

        given(couponRepository.findByCouponId(couponId)).willReturn(coupon);

        //when
        Coupon result = couponService.issue(couponId);

        //then
        assertThat(result.issuedCount()).isEqualTo(1);
        verify(couponRepository).findByCouponId(eq(couponId));
    }
}