package kr.hhplus.be.server.domain.coupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository {
    Coupon findByCouponId(Long couponId);
    void save(Coupon coupon);
}
