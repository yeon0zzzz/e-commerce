package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.support.aop.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @DistributedLock(key = "#couponId")
    @Transactional
    public Coupon issue(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId);

        Coupon issueCoupon = coupon.issue();

        return couponRepository.save(issueCoupon);
    }

    @Transactional
    public Coupon findById(Long couponId) {
        return couponRepository.findById(couponId);
    }

//    @Transactional
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
