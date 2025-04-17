package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon issue(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId);

        /*
         * TODO: 동시성 제어
         * */
        Coupon issueCoupon = coupon.issue();

        couponRepository.save(issueCoupon);

        return issueCoupon;
    }

    public Coupon findByCouponId(Long couponId) {
        return couponRepository.findById(couponId);
    }
}
