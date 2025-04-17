package kr.hhplus.be.server.infra.usercoupon;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserCouponRepositoryImpl implements UserCouponRepository {
    @Override
    public UserCoupon findById(Long userCouponId) {
        return null;
    }

    @Override
    public UserCoupon save(UserCoupon userCoupon) {
        return null;
    }

    @Override
    public List<UserCoupon> findAllByUserIdAndUsedIsFalse(Long userId) {
        return List.of();
    }
}
