package kr.hhplus.be.server.domain.coupon.usercoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    public UserCoupon use(Long userCouponId) {

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId);

        UserCoupon usedUserCoupon = userCoupon.use();

        return save(usedUserCoupon);
    }

    public List<UserCoupon> findUsableByUserId(Long userId) {
        return userCouponRepository.findAllByUserIdAndUsedIsFalse(userId);
    }

    @Transactional(readOnly = true)
    public UserCoupon findById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }

    public UserCoupon save(UserCoupon userCoupon) {
        return userCouponRepository.save(userCoupon);
    }

    public UserCoupon create(Long userId, Long couponId) {

        return UserCoupon.create(userId, couponId);
    }
}
