package kr.hhplus.be.server.domain.coupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository {
    Coupon findById(Long couponId);
    Coupon save(Coupon coupon);
    List<Coupon> findAllAvailable(LocalDateTime now);
}
