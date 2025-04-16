package kr.hhplus.be.server.domain.coupon.usercoupon;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    public UserCoupon use(Long userCouponId) {

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId);

        UserCoupon usedUserCoupon = userCoupon.use();

        userCouponRepository.save(usedUserCoupon);

        return usedUserCoupon;
    }

    public List<UserCoupon> findUsableByUserId(Long userId) {
        return userCouponRepository.findAllByUserIdAndUsedIsFalse(userId);
    }

    public UserCoupon findByUserCouponId(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }
}
