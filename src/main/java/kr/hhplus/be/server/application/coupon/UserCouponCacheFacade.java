package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponCacheFacade {

    private final UserCouponCacheService userCouponCacheService;

    public void publish(Long userId, Long couponId) {
        boolean issued = userCouponCacheService.publish(userId, couponId);
    }
}