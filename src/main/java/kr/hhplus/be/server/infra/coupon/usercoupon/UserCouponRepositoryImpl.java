package kr.hhplus.be.server.infra.coupon.usercoupon;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponRepository;
import kr.hhplus.be.server.infra.coupon.usercoupon.jpa.UserCouponEntity;
import kr.hhplus.be.server.infra.coupon.usercoupon.jpa.UserCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final UserCouponJpaRepository userCouponJpaRepository;

    @Override
    public UserCoupon findById(Long userCouponId) {
        return userCouponJpaRepository.findById(userCouponId)
                .map(UserCouponEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("사용자 쿠폰이 없습니다."));
    }

    @Override
    public UserCoupon save(UserCoupon userCoupon) {
        return UserCouponEntity.toDomain(userCouponJpaRepository.save(UserCouponEntity.toEntity(userCoupon)));
    }

    @Override
    public List<UserCoupon> findAllByUserIdAndUsedIsFalse(Long userId) {
        return userCouponJpaRepository.findAllByUserIdAndUsedIsFalse(userId)
                .stream()
                .map(UserCouponEntity::toDomain)
                .toList();
    }
}
