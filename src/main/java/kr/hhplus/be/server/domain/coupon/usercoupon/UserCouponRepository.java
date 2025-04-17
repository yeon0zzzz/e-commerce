package kr.hhplus.be.server.domain.coupon.usercoupon;

import java.util.List;

public interface UserCouponRepository {
    UserCoupon findById(Long userCouponId);
    UserCoupon save(UserCoupon userCoupon);
    List<UserCoupon> findAllByUserIdAndUsedIsFalse(Long userId);
}
