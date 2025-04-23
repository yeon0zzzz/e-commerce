package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon issue(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId);
        System.out.println(" ### service ##### ");
        System.out.println(coupon);

        /*
         * TODO: 동시성 제어
         * */
        Coupon issueCoupon = coupon.issue();

        return save(issueCoupon);
    }

    @Transactional(readOnly = true)
    public Coupon findById(Long couponId) {
        return couponRepository.findById(couponId);
    }

//    @Transactional
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
