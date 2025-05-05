package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponFacade {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @Transactional
    public UserCoupon issue(Long userId, Long couponId) {

        // 1. 쿠폰 발급(쿠폰 발급 한도 검증)
        Coupon issuedCoupon = couponService.issue(couponId);

        // 2. 사용자 쿠폰 객체 생성
        UserCoupon creatUserCoupon = userCouponService.create(userId, issuedCoupon.couponId());

        // 3. 사용자 쿠폰 저장
        return userCouponService.save(creatUserCoupon);
    }
}
